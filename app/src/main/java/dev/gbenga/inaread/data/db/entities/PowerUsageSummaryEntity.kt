package dev.gbenga.inaread.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity("power_usage_summary")
data class PowerUsageSummaryEntity (
    @PrimaryKey
    val fromDate: String,
    val periodInDays: String,
    val costPerKwh: BigDecimal,
    val meterType: String,
    val totalMonthPowerUsage: BigDecimal,
    val toDate: String,
    val totalSpent: BigDecimal
)