package uz.gita.foodapp_mehriddin.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.foodapp_mehriddin.presentation.add.AddDiraction
import uz.gita.foodapp_mehriddin.presentation.add.AddDiractionImpl
import uz.gita.foodapp_mehriddin.presentation.products.ProductDiraction
import uz.gita.foodapp_mehriddin.presentation.products.ProductDiractionImpl

@Module
@InstallIn(SingletonComponent::class)
interface DiractionModule {

    @Binds
    fun bindProductDiraction(impl:ProductDiractionImpl):ProductDiraction

    @Binds
    fun bindAddDiraction(impl:AddDiractionImpl):AddDiraction
}