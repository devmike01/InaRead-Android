package dev.gbenga.inaread.data.model

data class RefreshTokenRequest(
    val token: String,
    val username: String
)


data class RefreshTokenData(
    val token: String,
    val refreshToken: String,
    val expiryAt: String,
    val revoked: Boolean
)