package uz.gita.foodapp_mehriddin.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryItemBigItem(name:String) {
    Column(
        Modifier
            .height(40.dp)
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            )
            .background(
               color = Color.White,
                RoundedCornerShape(topEnd = 4.dp, topStart = 4.dp)
            )
            .padding(start = 14.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}