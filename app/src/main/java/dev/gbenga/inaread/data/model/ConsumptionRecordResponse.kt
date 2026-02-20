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
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val createdAt: Instant,
    val updatedAt: Instant? = null
)