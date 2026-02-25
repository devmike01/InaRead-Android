package dev.gbenga.inaread.data.model
import java.time.LocalDate

data class PowerUsageRequest(
    val meterCategoryId: Int=0,
    val fromDate: String,
    val toDate: String,
    val customerId: String="", // This is set at the repository
    val readingText: String
)
