package dev.gbenga.inaread.ui.signup

@Deprecated("Marked for removal because of internal loop in viewmodel")

sealed interface SignUpEvent {
    object SignUp : SignUpEvent
    object GotoLogin : SignUpEvent
}