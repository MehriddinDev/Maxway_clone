package uz.gita.foodapp_mehriddin.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import uz.gita.foodapp_mehriddin.R
import uz.gita.foodapp_mehriddin.app.App
import uz.gita.foodapp_mehriddin.util.ConnectInternet

@Composable
fun OrderedProductItem(
    imageUrl: String,
    price: Long,
    title: String,
    _productCount: Int,
    onClickDelete: () -> Unit,
    clickCount: (Int) -> Unit
) {
    Column() {
        Row(
            Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.White)
        ) {
            var productCount by remember { mutableStateOf(_productCount) }
            Column(
                Modifier
                    .fillMaxHeight()
                    .width(140.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                SubcomposeAsyncImage(
                    model = if(ConnectInternet(App.context))imageUrl else R.drawable.placeholder,
                    loading = {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(38.dp),
                                color = colorResource(
                                    id = R.color.ink
                                ),
                                strokeWidth = 1.5.dp
                            )
                        }
                    },
                    contentScale = ContentScale.Crop,
                    contentDescription = "",
                    alignment = Alignment.Center,
                )
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, end = 14.dp)
                    .background(Color.White)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 14.dp)
                        .background(Color.White),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Image(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Color.LightGray
                        ),
                        modifier = Modifier
                            .size(18.dp)
                            .clickable {
                                onClickDelete.invoke()
                            }
                    )

                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 14.dp, bottom = 14.dp),
                    Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$price so'm",
                        color = colorResource(id = R.color.black),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .height(35.dp)
                            .width(110.dp)
                            .border(
                                1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(8.dp)
                            )

                    ) {
                        TextButton(
                            onClick = {
                                --productCount
                                clickCount.invoke(productCount)
                            }, enabled = if (productCount < 2) false else true
                        ) {

                            Text(
                                text = "—",
                                fontSize = 14.sp,
                                color = if (productCount == 1) Color.LightGray else Color.Black
                            )
                        }

                        Text(
                            text = productCount.toString(),
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(end = 4.dp)
                        )


                        TextButton(
                            onClick = {
                                ++productCount
                                clickCount.invoke(productCount)
                            }, modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Text(text = "+", fontSize = 14.sp, color = Color.Black)
                        }

                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 14.dp)
                .background(Color.LightGray)
        )
    }

}

@Preview
@Composable
fun Prevvvv() {
    OrderedProductItem(imageUrl = "https://www.healthyseasonalrecipes.com/wp-content/uploads/2022/09/mediterranean-lavash-wraps-055.jpg",
        price = 34000L,
        title = "Lavash", 3, {}) {

    }
}