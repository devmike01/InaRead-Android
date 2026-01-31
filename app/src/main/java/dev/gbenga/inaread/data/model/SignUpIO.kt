package dev.gbenga.inaread.data.model

data class SignUpInput(
    val username: String,
    val password: String,
    val meterType: String,
    val countryId: String,
    val meterNo: String,
    val email: String,
    val meterCategoryId: Int
)

data class SignUpOutput(
    val message: String
)
