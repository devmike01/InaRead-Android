package dev.gbenga.inaread.data.model

import java.math.BigDecimal


data class YearlyUsageResponse(
    val periodInDays: String,
    val meterType: String,
    val totalMonthPowerUsage: BigDecimal,
    val fromDate: String,
    val toDate: String,
    val totalSpent: BigDecimal
)

