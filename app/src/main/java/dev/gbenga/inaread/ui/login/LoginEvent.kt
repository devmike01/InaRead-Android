package dev.gbenga.inaread.ui.login

@Deprecated("Marked for removal because of internal loop in viewmodel")

sealed interface LoginEvent {
    data class Login(val email: String, val password: String) : LoginEvent
    object ForgotPassword: LoginEvent
    object SignUp : LoginEvent
}