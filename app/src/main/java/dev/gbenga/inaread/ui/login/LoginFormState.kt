package dev.gbenga.inaread.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Stable
class LoginFieldState {

    var username by mutableStateOf("")
        private set
    //val email : String get() = _email

    var password by mutableStateOf("")
        private set


    fun onUsernameChanged(username: String){
        this.username = username
    }

    fun onPasswordChanged(password: String){
        this.password = password
    }

}

@Composable
fun rememberLoginFieldState(): LoginFieldState{
    return rememberSaveable(saver =LoginFieldStateSaver) {
        LoginFieldState()
    }
}

private val LoginFieldStateSaver = mapSaver(
    save = { state -> mapOf("username" to state.username)},
    restore = { map -> LoginFieldState().apply {
        onUsernameChanged(map["username"] as String)
    }}
)