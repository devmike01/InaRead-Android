package dev.gbenga.inaread.data.model

import java.math.BigDecimal

data class PowerUsageSummaryResponse(
    val periodInDays: String,
    val costPerKwh: Double,
    val meterType: String,
    val totalMonthPowerUsage: BigDecimal,
    val fromDate: String,
    val toDate: String,
    val totalSpent: Double
)