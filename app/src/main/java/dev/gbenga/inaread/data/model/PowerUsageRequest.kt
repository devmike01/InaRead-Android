package dev.gbenga.inaread.data.model
import java.time.LocalDate

data class PowerUsageRequest(
    val meterCategoryId: Long,
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val customerId: String, // This is set at the repository
    val readingText: String
)
