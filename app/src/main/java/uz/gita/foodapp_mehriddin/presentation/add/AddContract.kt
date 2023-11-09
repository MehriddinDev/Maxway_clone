package uz.gita.foodapp_mehriddin.presentation.add

import org.orbitmvi.orbit.ContainerHost
import uz.gita.foodapp_mehriddin.data.model.OrderedProductData
import uz.gita.foodapp_mehriddin.data.model.ProductData

interface AddContract {
    sealed interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val productData: ProductData? = null,
    )

    sealed interface SideEffect {
        data class Toast(val message: String) : SideEffect
    }

    sealed interface Intent {
        data class Add(val orderedProduct: OrderedProductData) : Intent
        object Back : Intent
    }
}