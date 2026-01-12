package dev.gbenga.inaread.ui.home

data class CalendarTileData(val month: String, val day: String, val dateInMillis: Long, val selected: Boolean)

internal object CalendarParamNames{
    const val ID = "id"
    const val MONTH = "month"
    const val DAY = "day"
    const val DAY_IN_MILLIS = "dayInMillis"
}

fun CalendarTileData.asMap(): Map<String, Any>{
    return mapOf(CalendarParamNames.ID to selected,
        CalendarParamNames.MONTH to month,
        CalendarParamNames.DAY to day,
        CalendarParamNames.DAY_IN_MILLIS to dateInMillis)
}



typealias CalendarTileWeekDays = List<CalendarTileData>
