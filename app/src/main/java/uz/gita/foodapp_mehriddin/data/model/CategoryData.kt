package uz.gita.foodapp_mehriddin.data.model

import uz.gita.foodapp_mehriddin.data.source.local.entity.CategoryEntity

data class CategoryData(
    val id:String,
    val name:String
){
    fun toEntity():CategoryEntity = CategoryEntity(id,name)
}