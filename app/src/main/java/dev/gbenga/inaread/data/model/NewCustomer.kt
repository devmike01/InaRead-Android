package dev.gbenga.inaread.data.model

data class NewCustomerResponse(
    val customerId: String,
    val username: String,
    val email: String,
    val password: String?,        // nullable
    val meterNo: String,
    val countryId: String,
    val meterCategoryId: Int,
    val createdAt: String,
    val updatedAt: String,
    val enabled: Boolean,
    val locked: Boolean,
    val authToken: String?        // nullable
)