package dev.gbenga.inaread.ui.waiting

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.domain.usecase.CheckUserAuthenticationUseCase
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaitingViewModel @Inject constructor(private val checkAuthUseCase: CheckUserAuthenticationUseCase) : InaReadViewModelV2<WaitingState>(WaitingState()) {

    init {
        checkLogin()
    }
    fun checkLogin(){
        viewModelScope.launch {
            checkAuthUseCase().let { isLoggedIn ->
                setState { it.copy(isLoading = false) }

                Log.d("WaitingViewModel", "CHECKING LOG IN...$isLoggedIn")
                navigate(NavigationEvent.NavigateTaskTop(InaScreen.Dashboard
                    .takeIf { isLoggedIn } ?: InaScreen.Login))
            }
        }
    }


}