package dev.gbenga.inaread.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val token: String,
    val username: String
)


@Serializable
data class RefreshTokenData(
    val token: String,
    val refreshToken: String,
    val expiryAt: String,
    val revoked: Boolean
)