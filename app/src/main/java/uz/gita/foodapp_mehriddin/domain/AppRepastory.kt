package uz.gita.foodapp_mehriddin.domain

import kotlinx.coroutines.flow.Flow
import uz.gita.foodapp_mehriddin.data.model.OrderedProductData
import uz.gita.foodapp_mehriddin.data.model.ProductData
import uz.gita.foodapp_mehriddin.data.model.ProductData2
import uz.gita.foodapp_mehriddin.data.source.local.entity.CategoryEntity
import uz.gita.foodapp_mehriddin.data.source.local.entity.OrderedProductEntity

interface AppRepastory {
    fun getAllProducts(): Flow<List<ProductData>>
    fun getSearchedProducts(query:String): Flow<List<ProductData>>
    fun getAllCategory():Flow<List<CategoryEntity>>
    fun getAllCategoriesId():Flow<Result<List<String>>>
    fun getProductsByCategory(categoryName_:String): Flow<Result<List<ProductData2>>>
    fun getRecommendedProduct():List<ProductData>

    fun saveProductForBusket(orderedProductData: OrderedProductData)
    fun getAllProductForBusket():Flow<List<OrderedProductEntity>>
    fun getAllOrderedProductsForHistory(): Flow<List<OrderedProductEntity>>
    fun updateProductInBusket(orderedProductData: OrderedProductData)
    fun updateAll(list:List<OrderedProductData>)
    fun deleteFromBusket(orderedProductData: OrderedProductData)
    fun deleteAllDataFromBusket(list:List<OrderedProductData>)
    fun deleteAllFromHistory()

    fun saveLastSelectedCategory(name:String)
    fun getLastSelectedCategory():Flow<String>


}