package dev.gbenga.inaread.ui.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.gbenga.inaread.ui.login.LoginFieldState

class SignUpFormState {

    var username by mutableStateOf("")
        private set
    //val email : String get() = _email

    var password by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set


    fun onUsernameChanged(username: String){
        this.username = username
    }

    fun onPasswordChanged(password: String){
        this.password = password
    }

    fun onConfirmPasswordChanged(confirmPassword: String){
        this.confirmPassword = confirmPassword
    }

    fun onEmailChanged(email: String){
        this.email = email
    }

}

@Composable
fun rememberSignUpFormState(): SignUpFormState{
    return remember { SignUpFormState() }
}