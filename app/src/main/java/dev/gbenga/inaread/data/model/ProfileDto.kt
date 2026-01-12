package dev.gbenga.inaread.data.model


data class ProfileResponse(
    val username: String,
    val email: String,
    val userId: String
)

data class ProfileRequest(
    val username: String,
    val email: String,
    val password: String,
)