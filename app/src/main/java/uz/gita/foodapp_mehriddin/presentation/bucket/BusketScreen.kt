package uz.gita.foodapp_mehriddin.presentation.bucket

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.foodapp_mehriddin.R
import uz.gita.foodapp_mehriddin.data.model.OrderedProductData
import uz.gita.foodapp_mehriddin.ui.component.OrderedProductItem
import uz.gita.foodapp_mehriddin.ui.component.PlaceHolder
import uz.gita.foodapp_mehriddin.ui.component.RecommendProductItem
import uz.gita.foodapp_mehriddin.util.myToast
import java.text.SimpleDateFormat
import java.util.*


object BusketScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(image = Icons.Outlined.ShoppingCart)

            return remember {
                TabOptions(0u, "Savatcha", icon)
            }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val viewModel: BusketContract.ViewModel = getViewModel<BusketViewModel>()
        val uiState = viewModel.collectAsState()

        viewModel.collectSideEffect {
            when (it) {
                is BusketContract.SideEffect.Toast -> {
                    myToast(it.m, context)
                }
            }
        }


        var deleteAllDialogOpeningValue by remember { mutableStateOf(false) }
        var orderedDialogOpeningValue by remember { mutableStateOf(false) }
        Scaffold(topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(Color.White),

                ) {

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Savatcha",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable {
                            if (uiState.value.products.isNotEmpty())
                                deleteAllDialogOpeningValue = true
                        }, colorFilter = ColorFilter.tint(Color.LightGray)
                )
            }
        },
            content = {
                BusketScreenContent(
                    uiState = uiState,
                    eventDispatcher = viewModel::onEventDispatcher,
                    deleteAllDialogOpeningValue,
                    orderedDialogOpeningValue,
                    it,
                    {
                        deleteAllDialogOpeningValue = false
                        if (it)
                            viewModel::onEventDispatcher.invoke(
                                BusketContract.Intent.DeleteAll(
                                    uiState.value.products
                                )
                            )
                    },
                    {
                        orderedDialogOpeningValue = false
                        if (it)
                            viewModel::onEventDispatcher.invoke(BusketContract.Intent.Order(uiState.value.products))
                    }
                )
            },
            bottomBar = {
                if (uiState.value.products.isNotEmpty()) {

                    Button(
                        onClick = {
                            if (uiState.value.products.isNotEmpty())
                                orderedDialogOpeningValue = true
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(horizontal = 14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.ink))
                    ) {
                        Text(
                            text = "Buyurtmani rasmiylashtirish",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            })

    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun BusketScreenContent(
    uiState: State<BusketContract.UIState>,
    eventDispatcher: (BusketContract.Intent) -> Unit,
    deleteAllDialogValue: Boolean,
    orderedDialogValue: Boolean,
    padding: PaddingValues,
    deleteAllOnClick: (Boolean) -> Unit,
    orderedOnClick: (Boolean) -> Unit
) {
    var dialogOpeningValue by remember { mutableStateOf(false) }
    var deleteAllDialogOpeningValue by remember { mutableStateOf(deleteAllDialogValue) }
    deleteAllDialogOpeningValue = deleteAllDialogValue
    var orderedDialogOpeningValue by remember { mutableStateOf(orderedDialogValue) }
    orderedDialogOpeningValue = orderedDialogValue
    var selectedData by remember {
        mutableStateOf(OrderedProductData(1, "", 0L, "", 0, false, ""))
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(padding)
            .background(colorResource(id = R.color.grey))
    ) {


        if (uiState.value.products.isNotEmpty()) {

            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
            )

            MyDialog(openingValue = dialogOpeningValue) {
                dialogOpeningValue = false
                if (it)
                    eventDispatcher.invoke(BusketContract.Intent.DeleteData(selectedData))
            }

            DialogDeleteAll(openingValue = deleteAllDialogOpeningValue) {
                deleteAllOnClick.invoke(it)
            }

            OrderedDialog(openingValue = orderedDialogOpeningValue, listener = {
                orderedOnClick.invoke(it)
            })

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                items(uiState.value.products) { product ->
                    var productCount by remember { mutableStateOf(product.count) }
                    OrderedProductItem(
                        imageUrl = product.imgUrl,
                        price = product.price * productCount,
                        title = product.title,
                        _productCount = productCount,
                        onClickDelete = {
                            dialogOpeningValue = true
                            selectedData = product
                        },
                        clickCount = {
                            productCount = it
                            eventDispatcher.invoke(BusketContract.Intent.Update(product.copy(count = productCount)))
                        }
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .background(
                                colorResource(
                                    id = R.color.grey
                                )
                            )
                    )
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp)

                            .background(Color.White), verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Tavsiya qilamiz",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 14.dp)
                        )
                    }
                    LazyRow {
                        items(uiState.value.recommendProducts) { product ->
                            RecommendProductItem(data = product) {
                                eventDispatcher.invoke(
                                    BusketContract.Intent.Add(
                                        OrderedProductData(
                                            0,
                                            product.imgUrl,
                                            product.price,
                                            product.title,
                                            1,
                                            false,
                                            getDate()
                                        )
                                    )
                                )
                            }
                        }
                    }
                }

            }
        } else {
            PlaceHolder(image = R.drawable.placeholder2, text = "Savatda hali mahsulot yo'q ")
        }

    }
}

@Composable
fun MyDialog(openingValue: Boolean, listener: (Boolean) -> Unit) {
    if (openingValue) {

        AlertDialog(
            onDismissRequest = {

            },
            title = {
                Text(
                    text = "Diqqat!",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "Haqiqatan ham mahsulotni ro'yhatdan olib tashlamoqchimisiz?",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { listener.invoke(true) }
                ) {
                    Text(
                        text = "Ha",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { listener.invoke(false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                        colorResource(
                            id = R.color.grey
                        )
                    )
                ) {
                    Text(
                        text = "Bekor qilish",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }, containerColor = Color.White
        )
    }
}

@Composable
fun DialogDeleteAll(openingValue: Boolean, listener: (Boolean) -> Unit) {
    if (openingValue) {

        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    text = "Diqqat!",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "Haqiqatan savatni bo'shatmoqchimisiz?",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { listener.invoke(true) }
                ) {
                    Text(
                        text = "Ha",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { listener.invoke(false) }, colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(
                            id = R.color.grey
                        )
                    )
                ) {
                    Text(
                        text = "Bekor qilish",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }, containerColor = Color.White
        )
    }
}

@Composable
fun OrderedDialog(openingValue: Boolean, listener: (Boolean) -> Unit) {
    if (openingValue) {

        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    text = "Diqqat!",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text(
                    text = "Buyurtmalar yuborilsinmi?",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = { listener.invoke(true) }
                ) {
                    Text(
                        text = "Ha",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { listener.invoke(false) }, colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(
                            id = R.color.grey
                        )
                    )
                ) {
                    Text(
                        text = "Bekor qilish",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }, containerColor = Color.White
        )
    }
}

private fun getDate(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val currentDate = Date()
    val formattedDate = dateFormat.format(currentDate)
    return formattedDate
}


