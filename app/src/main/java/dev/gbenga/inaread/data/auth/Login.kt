package dev.gbenga.inaread.data.auth

data class UiLogin(val value: String) {
}

data class LoginResponse(
    val customerId: String,
    val meterNo: String,
    val countryId: String,
    val meterCategoryId: Int,
    val createdAt: String,
    val enabled: Boolean,
    val username: String,
    val email: String,
    val authToken : AccessToken?,

)

data class AccessToken(val access: String,
                       val refresh: String,
                       val expiredIn: Long)

data class LoginRequest(val username: String,
    val password: String)

data class SignUpRequest(
    val username: String,
    val password: String,
    val email: String)


data class SignUpResponse(val message: String)