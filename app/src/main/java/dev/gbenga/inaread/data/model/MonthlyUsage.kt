package dev.gbenga.inaread.data.model

data class MonthlyUsage (
    val kilowatt: String,
    val period: String,
    val cost: String,
    val day: String,
    val dayOfMonth: String,
    val month: String
)



data class MonthlyUsageResponse
    (val date: String,
     val kilowatt: String,
     val period: String,
     val cost: String,)


data class MonthlyUsageRequest
    (val date: String,
     val kilowatt: String,
     val period: String,
     val cost: String)