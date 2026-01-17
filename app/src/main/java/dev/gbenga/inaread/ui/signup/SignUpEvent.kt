package dev.gbenga.inaread.ui.signup

sealed interface SignUpEvent {
    object SignUp : SignUpEvent
    object GotoLogin : SignUpEvent
}