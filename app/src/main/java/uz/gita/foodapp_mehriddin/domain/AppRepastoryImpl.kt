package uz.gita.foodapp_mehriddin.domain

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.foodapp_mehriddin.app.App
import uz.gita.foodapp_mehriddin.data.model.CategoryData
import uz.gita.foodapp_mehriddin.data.model.OrderedProductData
import uz.gita.foodapp_mehriddin.data.model.ProductData
import uz.gita.foodapp_mehriddin.data.model.ProductData2
import uz.gita.foodapp_mehriddin.data.source.local.dao.CategoryDao
import uz.gita.foodapp_mehriddin.data.source.local.dao.OrderedProductDao
import uz.gita.foodapp_mehriddin.data.source.local.dao.ProductDao
import uz.gita.foodapp_mehriddin.data.source.local.entity.CategoryEntity
import uz.gita.foodapp_mehriddin.data.source.local.entity.OrderedProductEntity
import uz.gita.foodapp_mehriddin.data.source.local.pref.MyPref
import uz.gita.foodapp_mehriddin.util.ConnectInternet
import uz.gita.foodapp_mehriddin.util.logger
import java.util.Collections
import javax.inject.Inject

class AppRepastoryImpl @Inject constructor(
    private val orderedDao: OrderedProductDao,
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao,
    private val pref: MyPref
) : AppRepastory {
    private val db = Firebase.firestore


    init {
        if (pref.getFirst() && ConnectInternet(App.context)) {
            logger("first", "OOO")
            //getAll PRODUCT and save room
            db.collection("products")
                .get()
                .addOnSuccessListener { allProducts ->
                    val products = arrayListOf<ProductData>()
                    var count = 0
                    allProducts.forEach {
                        logger("count = ${++count}", "FFF")
                        val product = ProductData(
                            0,
                            it.get("category_id") as String,
                            if (it.get("imageUrl") == null) "https://cdn-icons-png.flaticon.com/512/161/161542.png" else {
                                it.get("imageUrl") as String
                            },
                            it.get("info") as String,
                            it.get("price") as Long,
                            it.get("title") as String
                        )
                        products.add(product)
                    }

                    logger(products.size.toString() + "= list size", "FFF")
                    productDao.saveAllProducts(products.map { it.toEntity() })
                }
            //////////////////////////////////////////

            // get All category with id and save Room
            db.collection("categories")
                .get()
                .addOnSuccessListener { allCategories ->
                    val c = ArrayList<CategoryData>()
                    allCategories.documents.forEach {
                        c.add(CategoryData(it.id, it.get("title") as String))
                    }
                    categoryDao.saveAllCategory(c.map { it.toEntity() })
                }
            pref.saveFirst(false)
        }
    }

    /*override fun getAllProducts(): Flow<Result<List<ProductData>>> = callbackFlow {
        db.collection("products")
            .get()
            .addOnSuccessListener { allProducts ->
                val products = arrayListOf<ProductData>()

                allProducts.forEach {
                    val product = ProductData(
                        it.get("category_id") as String,
                        if (it.get("imageUrl") == null) "https://www.healthyseasonalrecipes.com/wp-content/uploads/2022/09/mediterranean-lavash-wraps-055.jpg" else {
                            it.get("imageUrl") as String
                        },
                        it.get("info") as String,
                        it.get("price") as Long,
                        it.get("title") as String
                    )
                    products.add(product)
                }
                trySend(Result.success(products))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }*/

    override fun getAllProducts(): Flow<List<ProductData>> {
        return productDao.getAllProducts()
    }

    /*@SuppressLint("SuspiciousIndentation")
    override fun getSearchedProducts(query: String): Flow<Result<List<ProductData>>> =
        callbackFlow {
            db.collection("products")
                .get()
                .addOnSuccessListener { allProducts ->
                    val products = arrayListOf<ProductData>()

                    allProducts.forEach {
                        val product = ProductData(0,
                            it.get("category_id") as String,
                            if (it.get("imageUrl") == null) "https://www.healthyseasonalrecipes.com/wp-content/uploads/2022/09/mediterranean-lavash-wraps-055.jpg" else {
                                it.get("imageUrl") as String
                            },
                            it.get("info") as String,
                            it.get("price") as Long,
                            it.get("title") as String
                        )
                        if (product.title.contains(query, ignoreCase = true))
                            products.add(product)
                    }
                    trySend(Result.success(products))
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }
            awaitClose()
        }*/

    override fun getSearchedProducts(query: String): Flow<List<ProductData>> {
        return productDao.getSearchedProducts(query)
    }

    /* override fun getAllCategory(): Flow<Result<List<String>>> = callbackFlow {
         db.collection("categories")
             .get()
             .addOnSuccessListener { allCategory ->
                 val categories = ArrayList<String>()
                 allCategory.forEach {
                     categories.add(it.get("title") as String)
                 }
                 trySend(Result.success(categories))

             }
             .addOnFailureListener {
                 trySend(Result.failure(it))
             }
         awaitClose()
     }*/

    override fun getAllCategory(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategory()
    }

    /*override fun getAllCategoriesId(): Flow<Result<List<String>>> = callbackFlow {
        db.collection("categories")
            .get()
            .addOnSuccessListener { allCategories ->
                val ids = ArrayList<String>()
                allCategories.documents.forEach {
                    ids.add(it.id)
                }
                trySend(Result.success(ids))
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }
        awaitClose()
    }*/

    override fun getAllCategoriesId(): Flow<Result<List<String>>> = callbackFlow {
        val list = arrayListOf<String>()
        categoryDao.getAllCategoriesId().forEach {
            list.add(it.id)
        }
        trySend(Result.success(list))
        awaitClose()
    }

    /*override fun getProductsByCategory(categoryName: String): Flow<Result<List<ProductData2>>> =
        callbackFlow {
            db.collection("categories").whereEqualTo("title", categoryName)
                .get()
                .addOnSuccessListener { category ->
                    val products2 = ArrayList<ProductData2>()
                    category.documents.forEach {
                        val id: String = it.id
                        db.collection("products").whereEqualTo("category_id", id)
                            .get()
                            .addOnSuccessListener { allProducts ->
                                val products = arrayListOf<ProductData>()

                                allProducts.forEach {
                                    val product = ProductData(0,
                                        it.get("category_id") as String,
                                        if (it.get("imageUrl") == null) "https://w7.pngwing.com/pngs/167/932/png-transparent-gray-burger-with-drink-fast-food-hamburger-symbol-delivery-chain-eating-fast-food-restaurant-icon-miscellaneous-angle-food-thumbnail.png" else {
                                            it.get("imageUrl") as String
                                        },
                                        it.get("info") as String,
                                        it.get("price") as Long,
                                        it.get("title") as String
                                    )
                                    products.add(product)
                                    return@forEach
                                }
                                products2.add(ProductData2(categoryName, products))
                                trySend(Result.success(products2))
                            }
                            .addOnFailureListener {
                                trySend(Result.failure(it))
                            }

                    }
                }
                .addOnFailureListener { trySend(Result.failure(it)) }
            awaitClose()
        }*/

    override fun getProductsByCategory(categoryName_: String): Flow<Result<List<ProductData2>>> =
        callbackFlow {
            val categoryData = categoryDao.getCategoryByName(categoryName_)
            val productData2List = arrayListOf<ProductData2>()


            val products = productDao.getAllProductsByCategoryId(categoryData.id)
            productData2List.add(ProductData2(categoryData.name, products))

            logger("categoryName = $categoryName_" + productData2List.toString(), "LKJ")

            trySend(Result.success(productData2List))
            awaitClose()
        }

    override fun getRecommendedProduct(): List<ProductData> {
        val products = productDao.getAllProductList()
        Collections.shuffle(products)
        val newProducts = products.subList(0,10)
        return newProducts
    }


    override fun saveProductForBusket(orderedProductData: OrderedProductData) {
        orderedDao.insert(orderedProductData.toEntity())
    }

    override fun getAllProductForBusket(): Flow<List<OrderedProductEntity>> {
        return orderedDao.getAllOrderedProductsForBusket()
    }

    override fun getAllOrderedProductsForHistory(): Flow<List<OrderedProductEntity>> =
        orderedDao.getAllOrderedProductsForHistory()

    override fun updateProductInBusket(orderedProductData: OrderedProductData) {
        orderedDao.update(orderedProductData.toEntity())
    }

    override fun updateAll(list: List<OrderedProductData>) {
        orderedDao.updateAll(list.map { it.toEntity().copy(isBuy = true) })
    }

    override fun deleteFromBusket(orderedProductData: OrderedProductData) {
        orderedDao.delete(orderedProductData.toEntity())
    }

    override fun deleteAllDataFromBusket(list: List<OrderedProductData>) {
        orderedDao.deleteAllFromBusket()
    }

    override fun deleteAllFromHistory() {
        orderedDao.deleteAllFromHistory()
    }

    override fun saveLastSelectedCategory(name: String) {
        pref.saveLastCategory(name)
    }

    override fun getLastSelectedCategory(): Flow<String> {
        return flow { emit(pref.getLastCategory()) }.flowOn(Dispatchers.IO)
    }
}