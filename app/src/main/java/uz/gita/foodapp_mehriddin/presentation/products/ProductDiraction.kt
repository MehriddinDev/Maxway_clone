package uz.gita.foodapp_mehriddin.presentation.products

import uz.gita.foodapp_mehriddin.navigation.AppNavigator
import uz.gita.foodapp_mehriddin.presentation.add.AddScreen
import javax.inject.Inject

interface ProductDiraction {
    suspend fun navigateToAddScreen(addScreen: AddScreen)
}

class ProductDiractionImpl @Inject constructor(
    private val appNavigator: AppNavigator
):ProductDiraction{
    override suspend fun navigateToAddScreen(addScreen: AddScreen) {
        appNavigator.navigateTo(addScreen)
    }

}