package uz.gita.foodapp_mehriddin.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.foodapp_mehriddin.domain.AppRepastory
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repastory: AppRepastory
):ViewModel(),HomeContract.ViewModel {

    override val container = container<HomeContract.UIState, HomeContract.SideEffect>(HomeContract.UIState())

    init {
        repastory.getAllProductForBusket().onEach {list->
            intent { reduce { state.copy(badgeCount = list.size) } }
        }.launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: HomeContract.Intent) {
        when(intent){
            HomeContract.Intent.Refresh -> {
                repastory.getAllProductForBusket().onEach {list->
                    intent { reduce { state.copy(badgeCount = list.size) } }
                }.launchIn(viewModelScope)
            }
        }
    }
}