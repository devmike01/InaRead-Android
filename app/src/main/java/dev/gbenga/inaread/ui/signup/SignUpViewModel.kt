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

    private val _navigator = Channel<NavigationEvent>(CONFLATED)
    val navigator get() = _navigator.receiveAsFlow()


    override fun watchEvents() {
        viewModelScope.launch {
            events.collect { uiEvents ->
                when(uiEvents){
                    is SignUpEvent.GotoLogin -> {
                        _navigator.trySend(NavigationEvent.Navigate(InaScreen.Login))
                    }
                    is SignUpEvent.SignUp -> {
                        setState(doBefore = {
                            it.copy(signUp = UiStateWithIdle.Loading)
                        }) { it.copy(signUp = UiStateWithIdle.Idle) }
                    }
                }
            }
        }
    }

    fun signUp(){
        sendEvent(SignUpEvent.SignUp)
    }

    fun gotoLogin(){
        sendEvent(SignUpEvent.GotoLogin)
    }

}