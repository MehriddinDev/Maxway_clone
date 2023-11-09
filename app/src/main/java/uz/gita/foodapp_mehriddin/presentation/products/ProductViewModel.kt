package uz.gita.foodapp_mehriddin.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.foodapp_mehriddin.data.model.ProductData2
import uz.gita.foodapp_mehriddin.domain.AppRepastory
import uz.gita.foodapp_mehriddin.presentation.add.AddScreen
import uz.gita.foodapp_mehriddin.util.logger
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repastory: AppRepastory,
    private val diraction: ProductDiraction
) : ViewModel(), ProductContract.ViewModel {

    override val container =
        container<ProductContract.UIState, ProductContract.SideEffect>(ProductContract.UIState())

    override fun onEventDispatcher(intent: ProductContract.Intent) {
        when (intent) {
            is ProductContract.Intent.Category -> {
                intent { reduce { state.copy(loading = true) } }
                repastory.saveLastSelectedCategory(intent.categoryName)

                repastory.getLastSelectedCategory().onEach {
                    logger(it + " viewmodel", "XXX")
                    intent { reduce { state.copy(loading = false,lastSelectedCategory = it) } }
                }.launchIn(viewModelScope)

                repastory.getProductsByCategory(intent.categoryName).onEach {
                    it.onSuccess {
                        logger("viewModel =" + it.size.toString(), "LKJ")
                        intent { reduce { state.copy(loading = false,productsWithCategory = it) } }
                    }
                }.launchIn(viewModelScope)
            }
            is ProductContract.Intent.Search -> {
                intent { reduce { state.copy(loading = true) } }
                viewModelScope.launch {
                    delay(100)
                }
                repastory.getSearchedProducts(intent.query).onEach { products ->
                    logger("searxhed list = $products", "FFF")
                    intent { reduce { state.copy(loading = false, searchedProducts = products) } }
                }.launchIn(viewModelScope)
            }
            ProductContract.Intent.AllProduct -> {
                getAllDatas()
            }
            is ProductContract.Intent.AddScreen -> {
                viewModelScope.launch {
                    diraction.navigateToAddScreen(AddScreen(intent.productData))
                }
            }
        }
    }

    init {
        intent { reduce { state.copy(loading = true) } }
        getCategories()

        var categoryNames = arrayListOf<String>()
        repastory.getAllCategory().onEach { categories ->
            categories.forEach {
                categoryNames.add(it.name)
            }
        }.launchIn(viewModelScope)

        repastory.getAllCategory().onEach {
            repastory.getAllCategoriesId().onEach {
                it.onSuccess { categoriesID ->
                    repastory.getAllProducts().onEach { products ->
                        val productsByCategory = ArrayList<ProductData2>()
                        categoriesID.forEachIndexed { index, category ->
                            val sortedProducts = products.filter {
                                it.categoryId == category
                            }
                            productsByCategory.add(
                                ProductData2(
                                    categoryNames[index],
                                    sortedProducts
                                )
                            )
                        }
                        if (productsByCategory.size > 0){
                            intent { reduce { state.copy(loading = false) } }
                        }
                        intent { reduce { state.copy(productsWithCategory = productsByCategory) } }
                    }.launchIn(viewModelScope)
                }
            }.launchIn(viewModelScope)
        }.launchIn(viewModelScope)

    }

    private fun getAllDatas() {
        var categoryNames = arrayListOf<String>()
        repastory.getAllCategory().onEach { categories ->
            categories.forEach {
                categoryNames.add(it.name)
            }
            intent { reduce { state.copy(categories = categoryNames) } }


        }.launchIn(viewModelScope)

        repastory.getAllCategory().onEach { categoriesID ->
            repastory.getAllProducts().onEach { products ->
                val productsByCategory = ArrayList<ProductData2>()
                categoriesID.forEachIndexed { index, category ->
                    val sortedProducts = products.filter {
                        it.categoryId == category.id
                    }
                    productsByCategory.add(
                        ProductData2(
                            categoryNames[index],
                            sortedProducts
                        )
                    )
                }
                intent {
                    reduce {
                        state.copy(
                            productsWithCategory = productsByCategory,
                            searchedProducts = emptyList()
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }.launchIn(viewModelScope)
    }

    private fun showToast(m: String) {
        intent { postSideEffect(ProductContract.SideEffect.Toast(m)) }
    }

    private fun getCategories() {
        repastory.getAllCategory().onEach { categories ->
            val list = arrayListOf<String>()
            categories.forEach {
                list.add(it.name)
            }
            intent { reduce { state.copy(categories = list) } }
        }.launchIn(viewModelScope)

    }

}