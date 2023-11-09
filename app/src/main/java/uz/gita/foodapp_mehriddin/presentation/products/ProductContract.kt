package uz.gita.foodapp_mehriddin.presentation.products

import org.orbitmvi.orbit.ContainerHost
import uz.gita.foodapp_mehriddin.data.model.OrderedProductData
import uz.gita.foodapp_mehriddin.data.model.ProductData
import uz.gita.foodapp_mehriddin.data.model.ProductData2

interface ProductContract {
    sealed interface ViewModel : ContainerHost<UIState, SideEffect>{
        fun onEventDispatcher(intent:Intent)
    }

   data class UIState (
       var loading:Boolean = true,
       val productsWithCategory:List<ProductData2> = emptyList(),
       val searchedProducts:List<ProductData> = emptyList(),
       val categories:List<String> = emptyList(),
       val lastSelectedCategory:String = ""
    )
    sealed interface SideEffect {
        data class Toast(val message:String):SideEffect
    }

    sealed interface Intent {
        data class Category(val categoryName:String):Intent
        data class Search(val query:String):Intent
        object AllProduct:Intent
        data class AddScreen(val productData: ProductData):Intent
    }
}