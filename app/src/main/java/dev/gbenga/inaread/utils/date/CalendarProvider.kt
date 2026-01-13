package dev.gbenga.inaread.utils.date

import dev.gbenga.inaread.domain.date.CalendarProvider
import dev.gbenga.inaread.tokens.StringTokens
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

data class DateAndMonth(val day: String, val month: String, val timeInMillis: Long)


class CalendarProviderImpl @Inject constructor(
    private val inaDateFormatter: InaDateFormatter
    ) : CalendarProvider {

    override fun getDateAndMonth(): List<DateAndMonth> {
        val calendar: Calendar = Calendar.getInstance()
        val dates = mutableListOf<DateAndMonth>()
        for (day in 0..6){
            calendar.add(Calendar.DAY_OF_MONTH, day)
            val dateMonth = inaDateFormatter
                .ddMMM(calendar.time)
                .split("/")
                .let {
                    DateAndMonth(it[0], it[1], calendar.timeInMillis)
                }
            dates.add(dateMonth)
        }
        return dates
    }

    override fun getTodayInMillis(): Long = System.currentTimeMillis()

    override fun getToday(): String = inaDateFormatter.dddMMMYyyy(Date())

    override fun greetBasedOnTime(): String {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        return "Good ${
            when (hourOfDay) {
                in 0..11 -> {
                    StringTokens.Greetings.GoodMorning
                }
                in 12..16 -> {
                    StringTokens.Greetings.GoodAfternoon
                }
                else -> {
                    StringTokens.Greetings.GoodEvening
                }
            }
        }"
    }

}