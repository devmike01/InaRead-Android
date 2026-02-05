package dev.gbenga.inaread.ui.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.domain.usecase.LoginUseCase
import dev.gbenga.inaread.ui.mapper.toUiStateLogin
import dev.gbenga.inaread.ui.validator.AuthFieldsValidator
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authFieldsValidator: AuthFieldsValidator,
    private val savedStateHandle: SavedStateHandle)
    : InaReadViewModelV2<LoginUiState>(LoginUiState.Empty) {

        companion object{
            const val ONESHOT = "ONESHOT"
        }


    @OptIn(ExperimentalCoroutinesApi::class)
    fun logIn(username: String, password: String){
        setState { it.copy(login = UiStateWithIdle.Idle) }
        savedStateHandle[ONESHOT] = true
        launchWithException(onError = { message ->
            setState { it.copy(login = UiStateWithIdle.Error(message)) }
        }){
            val user = authFieldsValidator.validateUsername(username).getOrThrow()
            val pass = authFieldsValidator.validatePassword(password).getOrThrow()
            setState { it.copy(login = UiStateWithIdle.Loading) }

            val loginRepoState =  loginUseCase(user, pass)

            setState {
                it.copy(login = loginRepoState.toUiStateLogin())
            }
        }

    }

    fun showSnackBar(message: String){
        if (savedStateHandle.get<Boolean>(ONESHOT) == true){
            showUiMessage(message)
       }
        savedStateHandle[ONESHOT] = false
    }


    fun navigateToDashboard(){
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