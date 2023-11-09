package uz.gita.foodapp_mehriddin.presentation.products

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import uz.gita.foodapp_mehriddin.R
import uz.gita.foodapp_mehriddin.ui.component.CategoryItem
import uz.gita.foodapp_mehriddin.ui.component.CategoryItemBigItem
import uz.gita.foodapp_mehriddin.ui.component.ProductItem
import uz.gita.foodapp_mehriddin.ui.component.SearchedProductItem
import uz.gita.foodapp_mehriddin.util.logger
import uz.gita.foodapp_mehriddin.util.myToast


object ProductsScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(image = Icons.Outlined.Home)

            return remember {
                TabOptions(0u, "Asosiy", icon)
            }
        }

    @Composable
    override fun Content() {
        val context = LocalContext.current

        val viewModel: ProductContract.ViewModel = getViewModel<ProductViewModel>()
        val uiState = viewModel.collectAsState()

        viewModel.collectSideEffect {
            when (it) {
                is ProductContract.SideEffect.Toast -> {
                    myToast(it.message, context)
                }
            }
        }

        ProductScreenContent(uiState, viewModel::onEventDispatcher)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProductScreenContent(
    uiState: State<ProductContract.UIState>,
    eventDispatcher: (ProductContract.Intent) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(uiState.value.lastSelectedCategory) }
    selectedCategory = uiState.value.lastSelectedCategory

    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.grey))
    ) {


        Column(
            Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.grey))
        ) {

            Column(Modifier.background(Color.White)) {
                Text(
                    text = "Fast Foods",
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Serif, fontStyle = FontStyle(700),
                    modifier = Modifier.padding(top = 10.dp, start = 20.dp),
                    color = colorResource(id = R.color.ink)
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 20.dp),
                    value = searchText,
                    onValueChange = {
                        if (it.length >= 3) {
                            eventDispatcher.invoke(ProductContract.Intent.Search(it))
                            logger("bo'shmas = $it", "KKK")
                        } else if (it.length < 3 && searchText.length >= 3) {
                            logger("bo'sh = $it", "KKK")
                            eventDispatcher.invoke(ProductContract.Intent.AllProduct)
                        }
                        searchText = it
                    },
                    placeholder = {
                        Text(
                            text = "Qidirish",
                            color = Color.LightGray,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        containerColor = colorResource(id = R.color.grey),
                        cursorColor = colorResource(id = R.color.ink)
                    ),
                    singleLine = false,
                    shape = RoundedCornerShape(14.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = colorResource(
                                id = R.color.ink
                            )
                        )
                    },
                    trailingIcon = {
                        if (searchText.isNotEmpty()) {
                            IconButton(onClick = { searchText = "" }) {
                                Icon(
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "Clear", tint = colorResource(id = R.color.ink)
                                )
                            }
                        }
                    })
                Spacer(modifier = Modifier.height(16.dp))


            }
            if (searchText.trim().length < 3 || searchText.isBlank()) {
                logger(uiState.value.loading.toString(), "OOO")
                if (!uiState.value.loading) {
                    logger("kirdi", "OOO")
                    LazyRow(Modifier.background(Color.White)) {
                        items(uiState.value.categories) { category ->
                            val textColor: Int
                            val backgroundColor: Int
                            logger(selectedCategory + " Row", "XXX")
                            if (category == selectedCategory) {
                                textColor = R.color.white
                                backgroundColor = R.color.ink
                            } else {
                                textColor = R.color.black
                                backgroundColor = R.color.grey
                            }
                            CategoryItem(
                                name = category,
                                textColor = textColor,
                                backgrounColor = backgroundColor
                            ) {
                                eventDispatcher.invoke(ProductContract.Intent.Category(category))
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                    )
                    LazyColumn {
                        item {
                            ImageSlider()
                        }

                        uiState.value.productsWithCategory.forEach { productData2 ->
                            item {
                                Spacer(modifier = Modifier.height(12.dp))
                                CategoryItemBigItem(name = productData2.categoryName)
                            }

                            items(productData2.products) { product ->
                                ProductItem(
                                    imageUrl = product.imgUrl,
                                    info = product.info,
                                    price = product.price,
                                    title = product.title
                                ) {
                                    eventDispatcher.invoke(ProductContract.Intent.AddScreen(product))
                                }
                            }
                        }


                    }
                }
            } else {
                if (uiState.value.searchedProducts.isNotEmpty()) {
                    LazyColumn(Modifier.background(colorResource(id = R.color.grey))) {
                        items(uiState.value.searchedProducts) { product ->
                            SearchedProductItem(
                                imageUrl = product.imgUrl,
                                title = product.title,
                                price = product.price
                            ) {
                                eventDispatcher.invoke(ProductContract.Intent.AddScreen(product))
                            }
                        }
                    }
                } else {

                    Column(
                        Modifier
                            .padding(bottom = 20.dp)
                            .fillMaxSize(),
                        Arrangement.Center,
                        Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.not_found),
                            contentDescription = ""
                        )
                        Text(text = "Mahsulot topilmadi", color = Color.Gray, fontSize = 16.sp)
                    }
                }
            }


        }

        if (uiState.value.loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp)
                    .padding(top = 40.dp), color = colorResource(
                    id = R.color.ink
                )
            )
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider() {
    Box() {
        HorizontalPager(
            pageCount = 3,
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .padding(horizontal = 10.dp, vertical = 17.dp)
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .align(Alignment.BottomCenter)
        ) {
            when (it) {
                0 -> {
                    Image(
                        painter = painterResource(id = R.drawable.slide_1),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                1 -> {
                    Image(
                        painter = painterResource(id = R.drawable.slide_2),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                2 -> {
                    Image(
                        painter = painterResource(id = R.drawable.slide_3),
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }


    }

}