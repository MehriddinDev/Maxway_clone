package uz.gita.foodapp_mehriddin.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import uz.gita.foodapp_mehriddin.R
import uz.gita.foodapp_mehriddin.app.App
import uz.gita.foodapp_mehriddin.data.model.ProductData
import uz.gita.foodapp_mehriddin.util.ConnectInternet

@Composable
fun RecommendProductItem(data: ProductData, onClick: () -> Unit) {

    Column(
        modifier = Modifier
            .width(130.dp)
            .height(220.dp)
            .background(colorResource(id = R.color.white))
            .padding(start = 4.dp, end = 4.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick.invoke() }
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.grey),
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.white))
                .height(100.dp)
        ) {
            SubcomposeAsyncImage(
                model = if (ConnectInternet(App.context)) data.imgUrl else R.drawable.placeholder,
                loading = {
                    Box(
                        Modifier
                            .fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = "", modifier = Modifier.background(Color.White)
                        )
                    }
                },
                contentScale = ContentScale.Crop,
                contentDescription = "",
            )
        }


        Spacer(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.grey))
        )
        Text(
            text = data.title, color = Color.Black, modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.grey)
                ), textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(colorResource(id = R.color.grey))
        )
        Column(
            Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.grey)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${data.price} so'm", color = Color.Black)
            }

        }
        Spacer(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.grey))
        )
    }

}