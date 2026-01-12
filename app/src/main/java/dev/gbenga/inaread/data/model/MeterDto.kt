package dev.gbenga.inaread.data.model

data class MeterResponse(
    val monthlyMeterUsage: MonthlyMeterUsage,
    val lastUsage: LastPowerUsage
)

// Monthly meter usage
data class MonthlyMeterUsage(
    val usages: List<Float>,
    val name: String,
    val kilowatts : Double,
    val minorCost: Long,
    val nairaPerKWhMinor: Long
)

data class LastPowerUsage(
    val kilowatts: Double,
    val periodInDays: Short,
    val minorCost : Long
)