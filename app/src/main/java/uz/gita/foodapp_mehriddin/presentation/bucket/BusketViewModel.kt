package uz.gita.foodapp_mehriddin.presentation.bucket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.foodapp_mehriddin.data.model.OrderedProductData
import uz.gita.foodapp_mehriddin.domain.AppRepastory
import uz.gita.foodapp_mehriddin.presentation.add.AddContract
import uz.gita.foodapp_mehriddin.util.logger
import javax.inject.Inject

@HiltViewModel
class BusketViewModel @Inject constructor(
    private val repastory: AppRepastory
):ViewModel(),BusketContract.ViewModel {

    override val container = container<BusketContract.UIState, BusketContract.SideEffect>(BusketContract.UIState())

    override fun onEventDispatcher(intent: BusketContract.Intent) {
        when(intent){
            is BusketContract.Intent.DeleteAll -> {
                repastory.deleteAllDataFromBusket(intent.datas)
            }
            is BusketContract.Intent.DeleteData -> {
                repastory.deleteFromBusket(intent.data)
            }
            is BusketContract.Intent.Update ->{
                repastory.updateProductInBusket(intent.data)
            }
            is BusketContract.Intent.Order -> {
                repastory.updateAll(intent.datas)
                intent{postSideEffect(BusketContract.SideEffect.Toast("Buyurtmalar yuborildi !"))}
            }
            is BusketContract.Intent.Add -> {
                var bool = false
                var bool2 = true
                repastory.getAllProductForBusket().onEach {

                    it.forEach {data->
                        if (data.imgUrl == intent.orderedProduct.imgUrl && data.price == intent.orderedProduct.price && data.title == intent.orderedProduct.title) {
                            bool = true
                            return@forEach
                        }
                    }
                    logger(bool2.toString(),"BOOL")
                    if (bool && bool2) {
                        intent { postSideEffect(BusketContract.SideEffect.Toast("Mahsulot savatchada mavjud")) }
                    } else if(bool2){
                        repastory.saveProductForBusket(intent.orderedProduct)
                        intent { postSideEffect(BusketContract.SideEffect.Toast("Mahsulot savatchaga qo'shildi")) }
                        bool2 = false
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    init {
        repastory.getAllProductForBusket().onEach {
            intent { reduce { state.copy(products = it.map { it.toData() }) } }
        }.launchIn(viewModelScope)

        intent { reduce { state.copy(recommendProducts = repastory.getRecommendedProduct()) } }


    }
}