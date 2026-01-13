package dev.gbenga.inaread.domain.home

import android.util.Log
import dev.gbenga.inaread.domain.date.CalendarProvider
import dev.gbenga.inaread.ui.home.CalendarTileData
import dev.gbenga.inaread.ui.home.CalendarTileWeekDays
import javax.inject.Inject

class WeekDaysUseCase @Inject constructor(private val calendarDelegate: CalendarProvider) {

    operator fun invoke(): CalendarTileWeekDays {
        return calendarDelegate.getDateAndMonth()
            .mapIndexed { index, item ->

                CalendarTileData(
                    item.month, item.day,
                    item.timeInMillis, index == 0
                )
            }
    }
}