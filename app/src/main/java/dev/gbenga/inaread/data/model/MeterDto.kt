package dev.gbenga.inaread.data.model

data class MeterResponse(
    val monthlyMeterUsage: MonthlyMeterUsage,
    val usages: List<LastPowerUsage>
)

// Monthly meter usage
data class MonthlyMeterUsage(
    val usages: List<Float>,
    val name: String,
    val kilowatts : Double,
    val minorCost: Long,
    val nairaPerKWhMinor: Long,
    val month: Int,
)

data class LastPowerUsage(
    val kilowatts: Double,
    val periodInDays: Short,
    val minorCost : Long,
    val fromDayOfMonth: Int, // from the day of the month
    val toDayOfMonth: Int, // to the day of the month
    val month: Int // The month from 1-12
)