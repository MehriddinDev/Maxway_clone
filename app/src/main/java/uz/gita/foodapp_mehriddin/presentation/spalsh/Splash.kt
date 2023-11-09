package uz.gita.foodapp_mehriddin.presentation.spalsh

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.foodapp_mehriddin.MainActivity
import uz.gita.foodapp_mehriddin.R
import uz.gita.foodapp_mehriddin.util.logger
@AndroidEntryPoint
class Splash : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        window.statusBarColor = resources.getColor(R.color.ink)
        logger("Keldi1","DDD")
        setContent {
            logger("Keldi2","DDD")
            Column(
                Modifier.background(color = colorResource(id = R.color.ink)),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                logger("Keldi3","DDD")
                Text(
                    text = "Fast Foods",
                    fontSize = 22.sp,
                    fontFamily = FontFamily.Serif, fontStyle = FontStyle(700),
                    modifier = Modifier.padding(top = 10.dp, start = 20.dp),
                    color = Color.Yellow
                )
            }
            CurrentScreen()

        }
        lifecycleScope.launch {
            delay(1000)
            val i = Intent(this@Splash,MainActivity::class.java)
            startActivity(i)
        }
    }
}