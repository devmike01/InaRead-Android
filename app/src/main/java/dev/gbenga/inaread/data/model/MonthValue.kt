package dev.gbenga.inaread.data.model

data class MonthValue(
    val month: String,
    val value: Float
)


data class MonthChartResponse(
    val month: String,
    val value: Float
)


fun MonthChartResponse.toMonthValue(): MonthValue{
    return MonthValue(
        month = month,
        value = value
    )
}