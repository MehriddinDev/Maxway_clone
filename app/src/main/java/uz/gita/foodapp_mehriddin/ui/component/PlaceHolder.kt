package uz.gita.foodapp_mehriddin.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import uz.gita.foodapp_mehriddin.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlaceHolder(image:Int,text:String) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Yellow), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center)
                .background(colorResource(id = R.color.grey)), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(text =text, color = Color.Gray, fontSize = 16.sp)

        }
    }
}

@Composable
fun Temp() {
    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(40) {
                    Text(text = "Salom", modifier = Modifier
                        .height(52.dp)
                        .fillMaxWidth())
                }
            }

            LazyRow (modifier = Modifier.wrapContentHeight()){
                items(10) {
                    Image(painter = painterResource(id = R.drawable.not_net), contentDescription = "", modifier = Modifier.size(100.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun TempPrev() {
    Temp()
}