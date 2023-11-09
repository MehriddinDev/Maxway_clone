package uz.gita.foodapp_mehriddin.presentation.history

import org.orbitmvi.orbit.ContainerHost
import uz.gita.foodapp_mehriddin.data.model.OrderedProductData

class HistoryContract {
    sealed interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onEventDispatcher(intent: Intent)
    }
    data class UIState(
        val products:List<OrderedProductData> = emptyList()
    )
    sealed interface SideEffect{

    }
    sealed interface Intent{
        object DeleteAll:Intent
    }
}