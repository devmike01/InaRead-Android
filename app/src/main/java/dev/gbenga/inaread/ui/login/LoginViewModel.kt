package dev.gbenga.inaread.ui.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : InaReadViewModel<LoginUiState, LoginEvent>(LoginUiState.Empty) {

    private val _navigator = Channel<NavigationEvent>(CONFLATED)
    val navigator get() = _navigator.receiveAsFlow()

    override fun watchEvents() {
        viewModelScope.launch {
            events.collect { uiEvents ->
                when(uiEvents){
                    is LoginEvent.ForgotPassword -> {
                        _navigator.trySend(
                            NavigationEvent
                                .Navigate(InaScreen.ForgotPassword))
                    }
                    is LoginEvent.Login -> {

                    }
                    is LoginEvent.SignUp -> {
                        _navigator.trySend(
                            NavigationEvent
                                .Navigate(InaScreen.SignUp))
                    }
                }
            }
        }
    }

    fun logIn(username: String, password: String){
        sendEvent(LoginEvent.Login(username, password = password))
    }

    fun gotoSignUp(){
        sendEvent(LoginEvent.SignUp)
    }

    fun gotoForgotPassword(){
        sendEvent(LoginEvent.ForgotPassword)
    }
}