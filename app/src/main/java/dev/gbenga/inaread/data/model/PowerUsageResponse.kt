package dev.gbenga.inaread.data.model

import java.math.BigDecimal
import java.time.Instant


data class PowerUsageResponse(
    val periodInDays: String,
    val costPerKwh: BigDecimal,
    val meterType: String,
    val totalMonthPowerUsage: BigDecimal,
    val fromDate: String,
    val toDate: String,
    val totalSpent: BigDecimal
)