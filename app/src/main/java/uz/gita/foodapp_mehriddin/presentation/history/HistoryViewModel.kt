package uz.gita.foodapp_mehriddin.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.foodapp_mehriddin.domain.AppRepastory
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repastory: AppRepastory
) : ViewModel(), HistoryContract.ViewModel {

    override val container =
        container<HistoryContract.UIState, HistoryContract.SideEffect>(HistoryContract.UIState())

    override fun onEventDispatcher(intent: HistoryContract.Intent) {
        when (intent) {
            HistoryContract.Intent.DeleteAll -> {
                repastory.deleteAllFromHistory()
            }
        }
    }

    init {
        repastory.getAllOrderedProductsForHistory().onEach {
            intent { reduce { state.copy(products = it.map { it.toData() }) } }
        }.launchIn(viewModelScope)
    }
}