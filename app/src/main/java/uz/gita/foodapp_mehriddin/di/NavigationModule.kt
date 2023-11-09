package uz.gita.foodapp_mehriddin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.foodapp_mehriddin.navigation.AppNavigator
import uz.gita.foodapp_mehriddin.navigation.NavigationDispatcher
import uz.gita.foodapp_mehriddin.navigation.NavigationHandler

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindAppNavigator(impl: NavigationDispatcher): AppNavigator

    @Binds
    fun bindNavigationHandler(impl: NavigationDispatcher): NavigationHandler
}