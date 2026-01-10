package dev.gbenga.inaread.ui.home

import java.net.IDN

data class CalendarTileData(val month: String, val day: String, val dateInMillis: Long, val id: Int)

internal object CalendarParamNames{
    const val ID = "id"
    const val MONTH = "month"
    const val DAY = "day"
    const val DAY_IN_MILLIS = "dayInMillis"
}

fun CalendarTileData.asMap(): Map<String, Any>{
    return mapOf(CalendarParamNames.ID to id,
        CalendarParamNames.MONTH to month,
        CalendarParamNames.DAY to day,
        CalendarParamNames.DAY_IN_MILLIS to dateInMillis)
}



typealias CalendarTileWeakDays = List<CalendarTileData>
