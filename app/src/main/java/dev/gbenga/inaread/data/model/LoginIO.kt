package dev.gbenga.inaread.data.model

import dev.gbenga.inaread.data.auth.AccessToken


data class LoginOutput(
    val customerId: String,
    val meterNo: String,
    val countryId: String,
    val meterCategoryId: Int,
    val createdAt: String,
    val enabled: Boolean,
    val username: String,
    val email: String,
    )


data class LoginInput(
    val username: String,
    val password: String
)