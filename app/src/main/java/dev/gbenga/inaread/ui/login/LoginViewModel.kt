package dev.gbenga.inaread.ui.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.model.LoginInput
import dev.gbenga.inaread.domain.repository.AuthRepository
import dev.gbenga.inaread.domain.usecase.LoginUseCase
import dev.gbenga.inaread.ui.mapper.toUiState
import dev.gbenga.inaread.ui.mapper.toUiStateLogin
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : InaReadViewModelV2<LoginUiState>(LoginUiState.Empty) {

    fun logIn(username: String, password: String){
        viewModelScope.launch {
            setState { it.copy(login = UiStateWithIdle.Loading) }
            try {
                val repoState = loginUseCase(username, password = password)
                setState { it.copy(login = repoState.toUiStateLogin()) }
            }catch (exception: Exception){
                setState { it.copy(
                    login = UiStateWithIdle.Error(exception.message)) }
            }
        }
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