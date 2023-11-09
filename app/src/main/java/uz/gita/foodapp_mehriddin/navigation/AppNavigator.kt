package uz.gita.foodapp_mehriddin.navigation

import cafe.adriel.voyager.androidx.AndroidScreen


typealias AppScreen = AndroidScreen

interface AppNavigator {
    suspend fun stackLog()

    suspend fun back()
    suspend fun backUntilRoot()
    suspend fun backAll()
    suspend fun navigateTo(screen: AppScreen)
    suspend fun replace(screen: AppScreen)
    suspend fun replaceAll(screen: AppScreen)
}