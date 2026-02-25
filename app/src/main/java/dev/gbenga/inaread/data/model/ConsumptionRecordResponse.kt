package dev.gbenga.inaread.data.model
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate

data class ConsumptionRecordResponse(
    val customerId: String,
    val currency: String,
    val consumptionKwh: BigDecimal,
    val costPerKWh: BigDecimal,
    val totalCost: BigDecimal,
    val fromDate: String,
    val toDate: String,
    val createdAt: String,
    val updatedAt: String? = null
)