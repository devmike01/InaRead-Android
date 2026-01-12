package dev.gbenga.inaread.domain.usescases.meter

import android.util.Log
import dev.gbenga.inaread.ui.home.CalendarTileData
import dev.gbenga.inaread.ui.home.CalendarTileWeekDays
import dev.gbenga.inaread.utils.date.CalendarProvider
import javax.inject.Inject

class WeekDaysUseCase @Inject constructor(private val calendarDelegate: CalendarProvider) {

    operator fun invoke(): CalendarTileWeekDays {
        return calendarDelegate.getDateAndMonth()
            .mapIndexed { index, item ->
                Log.d("WeekDaysUseCase", "${item.month}")
                CalendarTileData(
                    item.month, item.day,
                    item.timeInMillis, index == 0
                )
            }
    }
}