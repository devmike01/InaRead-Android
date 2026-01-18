package dev.gbenga.inaread.ui.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : InaReadViewModel<LoginUiState, LoginEvent>(LoginUiState.Empty) {


    fun logIn(username: String, password: String){
        navigate(
            NavigationEvent
                .NavigateTaskTop(InaScreen.Dashboard))
    }

    fun gotoSignUp(){
        navigate(
            NavigationEvent
                .Navigate(InaScreen.SignUp))
    }

    fun gotoForgotPassword(){
        navigate(
            NavigationEvent
                .Navigate(InaScreen.ForgotPassword))
    }
}