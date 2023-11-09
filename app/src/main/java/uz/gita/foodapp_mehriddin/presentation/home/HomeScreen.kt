package uz.gita.foodapp_mehriddin.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.foodapp_mehriddin.R
import uz.gita.foodapp_mehriddin.navigation.AppScreen
import uz.gita.foodapp_mehriddin.presentation.bucket.BusketScreen
import uz.gita.foodapp_mehriddin.presentation.history.HistoryScreen
import uz.gita.foodapp_mehriddin.presentation.products.ProductsScreen
import uz.gita.foodapp_mehriddin.util.logger

class HomeScreen : AppScreen() {
    @SuppressLint("StateFlowValueCalledInComposition")
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = getViewModel()
        val badgeCount = viewModel.collectAsState().value.badgeCount
        logger(badgeCount.toString(), "ZZZ")
        HomeScreenContent(badgeCount)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(badgeCount: Int) {
    TabNavigator(ProductsScreen) {
        Scaffold(bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .height(74.dp)
            ) {
                TabNavigatorItem(tab = ProductsScreen, badgeCount, false)
                TabNavigatorItem(tab = BusketScreen, badgeCount, true)
                TabNavigatorItem(tab = HistoryScreen, badgeCount, false)
                logger("Click1","SSS")
            }
        },
            content = {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    CurrentTab()
                }
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RowScope.TabNavigatorItem(tab: Tab, badgeCount: Int, bool: Boolean) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        label = {
            logger("Click3","SSS")

            Text(
                text = tab.options.title,
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 12.sp,
                color = if (tabNavigator.current == tab) colorResource(id = R.color.ink) else Color.LightGray
            )
        },
        icon = {
            if (bool) {
                if (badgeCount > 0) {
                    BadgedBox(badge = {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.Red), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = badgeCount.toString(),
                                color = Color.White,
                                modifier = Modifier
                                    .background(Color.Red)
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 10.sp
                            )
                        }
                    }) {
                        Icon(
                            painter = tab.options.icon!!,
                            contentDescription = null,
                            tint = if (tabNavigator.current == tab) colorResource(id = R.color.ink) else Color.LightGray
                        )
                    }
                } else {
                    Icon(
                        painter = tab.options.icon!!,
                        contentDescription = null,
                        tint = if (tabNavigator.current == tab) colorResource(id = R.color.ink) else Color.LightGray
                    )
                }

            }else {
                Icon(
                    painter = tab.options.icon!!,
                    contentDescription = null,
                    tint = if (tabNavigator.current == tab) colorResource(id = R.color.ink) else Color.LightGray
                )
            }

        })
}