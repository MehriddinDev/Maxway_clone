package uz.gita.foodapp_mehriddin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.foodapp_mehriddin.domain.AppRepastory
import uz.gita.foodapp_mehriddin.domain.AppRepastoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepastoryModule {

    @[Binds Singleton]
    fun bindRepastory(impl: AppRepastoryImpl): AppRepastory
}