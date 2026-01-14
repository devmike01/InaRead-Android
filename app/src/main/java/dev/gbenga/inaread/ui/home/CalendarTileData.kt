package dev.gbenga.inaread.ui.home

data class CalendarTileData(val month: String,
                            val monthValue: Int,
                            val dayOfMonth: Int,
                            val dayIsAvailable: Boolean = false,
                            val dateInMillis: Long = 0L,
                            val selected: Boolean = false)

internal object CalendarParamNames{
    const val ID = "id"
    const val MONTH = "month"
    const val DAY = "day"
    const val MONTH_VALUE = "month_value"
    const val DAY_IN_MILLIS = "dayInMillis"
    const val IS_AVAILABLE = "day_is_Available"
}

fun CalendarTileData.asMap(): Map<String, Any>{
    return mapOf(CalendarParamNames.ID to selected,
        CalendarParamNames.MONTH to month,
        CalendarParamNames.DAY to dayOfMonth,
        CalendarParamNames.DAY_IN_MILLIS to dateInMillis,
        CalendarParamNames.IS_AVAILABLE to dayIsAvailable
    )
}



typealias CalendarTileWeekDays = List<CalendarTileData>
