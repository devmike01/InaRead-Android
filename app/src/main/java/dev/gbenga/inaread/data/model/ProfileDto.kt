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
    val customerId: String,
    val username: String,
    val email: String,
    val meterNo: String,
    val countryId: String,
    val meterCategoryId: Int,
    val createdAt: String,
    val updatedAt: String,
    val enabled: Boolean,
    val locked: Boolean,
    val initial: String =""
){

}