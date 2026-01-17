package dev.gbenga.inaread.ui.login

sealed interface LoginEvent {
    data class Login(val email: String, val password: String) : LoginEvent
    object ForgotPassword: LoginEvent
    object SignUp : LoginEvent
}