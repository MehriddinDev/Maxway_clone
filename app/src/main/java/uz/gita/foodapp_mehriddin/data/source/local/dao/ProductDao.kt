package uz.gita.foodapp_mehriddin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.gita.foodapp_mehriddin.data.model.ProductData
import uz.gita.foodapp_mehriddin.data.source.local.entity.ProductEntity

@Dao
interface ProductDao {
    @Insert
    fun saveAllProducts(products:List<ProductEntity>)

    @Query("SELECT * FROM myProducts")
    fun getAllProducts(): Flow<List<ProductData>>

    @Query("SELECT * FROM myProducts")
    fun getAllProductList(): List<ProductData>

    @Query("SELECT * FROM myProducts WHERE categoryId = :categoryId")
    fun getAllProductsByCategoryId(categoryId:String): List<ProductData>

    @Query("SELECT * FROM myProducts WHERE title LIKE '%' || :query || '%'")
    fun getSearchedProducts(query:String): Flow<List<ProductData>>


}