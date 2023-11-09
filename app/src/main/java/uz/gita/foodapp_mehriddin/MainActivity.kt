package uz.gita.foodapp_mehriddin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.foodapp_mehriddin.navigation.NavigationHandler
import uz.gita.foodapp_mehriddin.presentation.home.HomeScreen
import uz.gita.foodapp_mehriddin.util.ConnectInternet
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler

    private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepVisibleCondition{
                viewModel.isLoading.value
            }
        }
        super.onCreate(savedInstanceState)
        window.statusBarColor = resources.getColor(R.color.white)

        setContent {
            var isConnect by remember { mutableStateOf(ConnectInternet(this)) }
            if (isConnect) {
                Navigator(screen = HomeScreen()) { navigator ->
                    LaunchedEffect(navigator) {
                        navigationHandler.navigationStack
                            .onEach { it.invoke(navigator) }
                            .launchIn(lifecycleScope)
                    }
                    CurrentScreen()
                }
            } else {
                NotNetworkContent{
                    if(ConnectInternet(this)){
                        isConnect = true
                    }else{
                        Toast.makeText(this, "Internet yo'q", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

@Composable
fun NotNetworkContent(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center)
                .background(Color.White), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.not_net),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Internet yo'q",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Internetga ulanishni tekshiring !", color = Color.Gray, fontSize = 14.sp)

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onClick.invoke() },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.ink))
            ) {
                Text(
                    text = "Qaytadan urinib ko'ring",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}