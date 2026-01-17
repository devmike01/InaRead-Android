package dev.gbenga.inaread.data.model

import androidx.compose.runtime.Immutable


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

@Immutable
data class Profile(
    val username: String ="",
    val email: String ="",
    val initial: String =""
){
    companion object {
        val EMPTY = Profile("", "", "")
    }
}