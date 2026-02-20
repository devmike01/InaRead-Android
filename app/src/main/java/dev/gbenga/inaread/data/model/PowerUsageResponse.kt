package dev.gbenga.inaread.data.model

import java.math.BigDecimal
import java.time.Instant


data class PowerUsageResponse(
    val periodInDays: String,
    val costPerKwh: BigDecimal,
    val meterType: String,
    val totalMonthPowerUsage: BigDecimal,
    val fromDate: Instant,
    val toDate: Instant,
    val totalSpent: BigDecimal
)