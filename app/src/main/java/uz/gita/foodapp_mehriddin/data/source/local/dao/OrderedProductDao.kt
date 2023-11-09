package uz.gita.foodapp_mehriddin.data.source.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.gita.foodapp_mehriddin.data.source.local.entity.OrderedProductEntity

@Dao
interface OrderedProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(orderedProductEntity: OrderedProductEntity)

    @Delete
    fun delete(orderedProductEntity: OrderedProductEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(orderedProductEntity: OrderedProductEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(list:List<OrderedProductEntity>)

    @Query("SELECT * FROM OrderedProducts where isBuy = 0")
    fun getAllOrderedProductsForBusket(): Flow<List<OrderedProductEntity>>

    @Query("SELECT * FROM OrderedProducts where isBuy = 1")
    fun getAllOrderedProductsForHistory(): Flow<List<OrderedProductEntity>>

    @Query("DELETE FROM OrderedProducts where isBuy = 0")
    fun deleteAllFromBusket()

    @Query("DELETE FROM OrderedProducts where isBuy = 1")
    fun deleteAllFromHistory()
}