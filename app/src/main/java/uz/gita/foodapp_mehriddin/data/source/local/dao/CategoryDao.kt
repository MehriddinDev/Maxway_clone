package uz.gita.foodapp_mehriddin.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.gita.foodapp_mehriddin.data.model.CategoryData
import uz.gita.foodapp_mehriddin.data.model.ProductData2
import uz.gita.foodapp_mehriddin.data.source.local.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllCategory(list:List<CategoryEntity>)

    @Query("SELECT * FROM category")
    fun getAllCategory(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM category")
    fun getAllCategoriesId(): List<CategoryData>

    @Query("SELECT * FROM category WHERE name = :categoryName")
    fun getCategoryByName(categoryName:String):CategoryEntity
//    fun getProductsByCategory(categoryName:String): Flow<Result<List<ProductData2>>>

}