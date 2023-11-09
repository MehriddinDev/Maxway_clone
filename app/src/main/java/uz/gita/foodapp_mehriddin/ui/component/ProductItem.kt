package uz.gita.foodapp_mehriddin.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
fun ProductItem(imageUrl: String, info: String, price: Long, title: String,onClick:()->Unit) {
    Column(Modifier.fillMaxWidth().clickable { onClick.invoke() }) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.White)
        ) {
            Column(Modifier.weight(1f).padding(vertical = 12.dp,horizontal = 10.dp)) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.weight(1f))
                Text(text = info, fontSize = 12.sp)
                Spacer(Modifier.weight(1f))
                Text(
                    text = price.toString(),
                    color = colorResource(id = R.color.ink),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(Modifier.height(4.dp))
            }
            Column(
                Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .padding(14.dp)
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
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.2.dp)
                .background(
                    color = colorResource(
                        id = R.color.grey
                    )
                )
                .padding(horizontal = 40.dp)

        )
    }

}

@Preview
@Composable
fun ItemPrev() {
    ProductItem(
        imageUrl = "https://www.healthyseasonalrecipes.com/wp-content/uploads/2022/09/mediterranean-lavash-wraps-055.jpg",
        info = "Good food",
        price = 34000L,
        title = "Lavash"
    ){}
}