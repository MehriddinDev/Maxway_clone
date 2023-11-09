package uz.gita.foodapp_mehriddin.presentation.add

import uz.gita.foodapp_mehriddin.navigation.AppNavigator
import javax.inject.Inject

interface AddDiraction {
    suspend fun Back()
}

class AddDiractionImpl @Inject constructor(
    private val appNavigator: AppNavigator
):AddDiraction{
    override suspend fun Back() {
        appNavigator.back()
    }

}