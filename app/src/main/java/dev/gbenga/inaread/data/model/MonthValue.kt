package dev.gbenga.inaread.data.model

data class MonthValue(
    val month: String,
    val value: Float
){
    companion object{
        val Empty = MonthValue("", 0f)
    }
}


data class MonthChartResponse(
    val month: String,
    val value: Float,
    val timeOfUse: String,
)


fun MonthChartResponse.toMonthValue(): MonthValue{
    return MonthValue(
        month = month,
        value = value
    )
}