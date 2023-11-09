package uz.gita.foodapp_mehriddin.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import uz.gita.foodapp_mehriddin.R
import uz.gita.foodapp_mehriddin.app.App
import uz.gita.foodapp_mehriddin.util.ConnectInternet

@Composable
fun SearchedProductItem(imageUrl: String, title: String, price: Long, onClick:()->Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(start = 14.dp, end = 14.dp, top = 10.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .clickable { onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .height(70.dp)
                .width(70.dp)
                .padding(horizontal = 10.dp, vertical = 12.dp)
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
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .border(
                        1.dp, colorResource(
                            id = R.color.ink
                        ), RoundedCornerShape(6.dp)
                    )
            )
        }

        Text(text = title, fontSize = 16.sp, modifier = Modifier.padding(start = 6.dp))
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$price so'm",
            color = colorResource(id = R.color.black),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(end = 10.dp)
        )

    }
}