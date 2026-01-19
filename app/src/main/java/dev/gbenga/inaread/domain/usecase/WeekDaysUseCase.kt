package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.domain.providers.CalendarProvider
import dev.gbenga.inaread.ui.home.CalendarTileData
import dev.gbenga.inaread.ui.home.CalendarTileWeekDays
import javax.inject.Inject

class WeekDaysUseCase @Inject constructor(private val calendarDelegate: CalendarProvider) {

    operator fun invoke(): CalendarTileWeekDays {
        return calendarDelegate.getDateAndMonth()
            .mapIndexed { index, item ->

                CalendarTileData(
                    item.month,
                    item.monthValue,
                    item.dayOfMonth,
                    false,
                    item.timeInMillis,
                    index == 0
                )
            }
    }
}