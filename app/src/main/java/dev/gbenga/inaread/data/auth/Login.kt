package dev.gbenga.inaread.data.auth

data class UiLogin(val value: String) {
}

data class LoginResponse(val value: String)

data class LoginRequest(val username: String,
    val password: String)