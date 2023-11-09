package uz.gita.foodapp_mehriddin.data.model

import uz.gita.foodapp_mehriddin.data.source.local.entity.OrderedProductEntity
import uz.gita.foodapp_mehriddin.data.source.local.entity.ProductEntity

data class ProductData(
    val id:Int,
    val categoryId: String,
    val imgUrl: String,
    val info: String,
    val price: Long,
    val title: String
){
    fun toEntity():ProductEntity = ProductEntity(id,categoryId, imgUrl, info, price, title)
}
