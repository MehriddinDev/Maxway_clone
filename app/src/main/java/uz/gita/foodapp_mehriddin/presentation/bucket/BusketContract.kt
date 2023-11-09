package uz.gita.foodapp_mehriddin.presentation.bucket

import org.orbitmvi.orbit.ContainerHost
import uz.gita.foodapp_mehriddin.data.model.OrderedProductData
import uz.gita.foodapp_mehriddin.data.model.ProductData

interface BusketContract {
    sealed interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onEventDispatcher(intent:Intent)
    }
    data class UIState(
        val products:List<OrderedProductData> = emptyList(),
        val recommendProducts: List<ProductData> = emptyList()
    )
    sealed interface SideEffect{
        data class Toast(val m:String):SideEffect
    }
    sealed interface Intent{
        data class DeleteData(val data:OrderedProductData):Intent
        data class DeleteAll(val datas:List<OrderedProductData>):Intent
        data class Update(val data:OrderedProductData):Intent
        data class Order(val datas:List<OrderedProductData>):Intent
        data class Add(val orderedProduct: OrderedProductData):Intent
    }
}