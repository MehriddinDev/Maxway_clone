package uz.gita.foodapp_mehriddin.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryItem(name: String, textColor: Int, backgrounColor: Int, onClick: () -> Unit) {
    Column(
        Modifier
            .height(34.dp)
            .padding(horizontal = 6.dp)
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(
                colorResource(id = backgrounColor),
                RoundedCornerShape(4.dp)
            )
            .clickable {
                onClick.invoke()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = name,
            fontSize = 14.sp,
            color = colorResource(id = textColor),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}


