package uz.gita.foodapp_mehriddin.presentation.home

import org.orbitmvi.orbit.ContainerHost

interface HomeContract {
    sealed interface ViewModel:ContainerHost<UIState,SideEffect>{
        fun onEventDispatcher(intent: Intent)
    }
    data class UIState(
        val badgeCount :Int = 0
    )
    sealed interface SideEffect{}
    sealed interface Intent{
        object Refresh:Intent
    }
}