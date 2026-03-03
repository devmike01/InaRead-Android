package dev.gbenga.inaread.data.model

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class MonthlyUsage (
    val kilowatt: String ="",
    val period: String="",
    val cost: String="",
    val day: String="",
    val dayOfMonth: String="",
    val month: String="",
    val fromDate: String="",
    val toDate: String="",
    val meterType: String="",
  //  val powerConsumption: BigDecimal ="",
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