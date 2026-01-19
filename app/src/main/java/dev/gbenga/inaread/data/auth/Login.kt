package dev.gbenga.inaread.data.auth

data class UiLogin(val value: String) {
}

data class LoginResponse(
    val username: String,
    val email: String,
    val access : AccessToken
)

data class AccessToken(val access: String, val refresh: String)

data class LoginRequest(val username: String,
    val password: String)

data class SignUpRequest(
    val username: String,
    val password: String,
    val email: String)


data class SignUpResponse(val message: String)