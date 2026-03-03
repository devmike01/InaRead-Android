package dev.gbenga.inaread.ui.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.datastore.Messenger
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.domain.usecase.LoginUseCase
import dev.gbenga.inaread.ui.mapper.toUiStateLogin
import dev.gbenga.inaread.ui.validator.AuthFieldsValidator
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authFieldsValidator: AuthFieldsValidator,
    private val savedStateHandle: SavedStateHandle,
    private val messenger: Messenger)
    : InaReadViewModelV2<LoginUiState>(LoginUiState.Empty) {

        companion object{
            const val ONESHOT = "ONESHOT"
        }

    init {

        receiveMessage()
    }

    fun receiveMessage(){
        viewModelScope.launch {
            messenger.receiveMessage().collect { msg ->
                Log.d("viewModelScope", "msg: $msg")
                showUiMessage(msg)
            }
        }
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
            when(loginRepoState){
                is RepoResult.Success -> {
                    navigate(
                        NavigationEvent
                            .NavigateTaskTop(InaScreen.Dashboard))
                }
                is RepoResult.Error -> {
                    showSnackBar(loginRepoState.message)
                }
            }
            setState {
                it.copy(login = loginRepoState.toUiStateLogin())
            }
        }

    }

    fun showSnackBar(message: String?){
        if (message == null)return
        if (savedStateHandle.get<Boolean>(ONESHOT) == true){
            showUiMessage(message)
       }
        savedStateHandle[ONESHOT] = false
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