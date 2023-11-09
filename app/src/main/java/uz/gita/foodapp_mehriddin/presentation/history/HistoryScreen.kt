package uz.gita.foodapp_mehriddin.presentation.history

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.foodapp_mehriddin.R
import uz.gita.foodapp_mehriddin.presentation.bucket.DialogDeleteAll
import uz.gita.foodapp_mehriddin.ui.component.HistoryProductItem
import uz.gita.foodapp_mehriddin.ui.component.PlaceHolder

object HistoryScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {

            val icon = rememberVectorPainter(image = Icons.Outlined.DateRange)
            return remember {
                TabOptions(0u, "Buyurtmalarim", icon)
            }
        }

    @Composable
    override fun Content() {
        val viewModel: HistoryContract.ViewModel = getViewModel<HistoryViewModel>()
        val uiState = viewModel.collectAsState()
        HistoryScreenContent(uiState, viewModel::onEventDispatcher)
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun HistoryScreenContent(
    uiState: State<HistoryContract.UIState>,
    eventDispatcher: (HistoryContract.Intent) -> Unit
) {
    Column(Modifier.background(colorResource(id = R.color.grey))) {
        var deleteAllDialogOpeningValue by remember { mutableStateOf(false) }
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
                text = "Buyurtmalarim",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))

            Image(
                imageVector =  Icons.Outlined.Delete,
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable {
                        if (uiState.value.products.isNotEmpty())
                            deleteAllDialogOpeningValue = true
                    }, colorFilter = ColorFilter.tint(Color.LightGray)
            )
        }

      

       if(uiState.value.products.isNotEmpty()){
           Spacer(
               modifier = Modifier
                   .height(10.dp)
                   .fillMaxWidth()
           )
           LazyColumn(
               modifier = Modifier
                   .fillMaxSize()
                   .background(colorResource(id = R.color.grey))
           ) {
               items(uiState.value.products) { product ->

                   HistoryProductItem(
                       imageUrl = product.imgUrl,
                       price = product.price * product.count,
                       title = product.title,
                       _productCount = product.count,
                       date = product.day
                   )
               }
           }
       }else{
           PlaceHolder(image = R.drawable.placeholder3, text = "Buyurtma mavjud emas")
       }
       

        DialogDeleteAll2(openingValue = deleteAllDialogOpeningValue) {
            deleteAllDialogOpeningValue = false
            if (it)
                eventDispatcher.invoke(HistoryContract.Intent.DeleteAll)

        }
    }
}

@Composable
fun DialogDeleteAll2(openingValue: Boolean, listener: (Boolean) -> Unit) {
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
                    text = "Haqiqatan buyurtmalar tarixini o'cirmoqchimisiz?",
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