package dev.gbenga.inaread.ui.signup

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : InaReadViewModel<SignUpUiState, SignUpEvent> (SignUpUiState()){



    fun signUp(){
        setState(doBefore = {
            it.copy(signUp = UiStateWithIdle.Loading)
        }) { it.copy(signUp = UiStateWithIdle.Idle) }
    }

    fun gotoLogin(){
        navigate(NavigationEvent.NavigateBack)
    }


}