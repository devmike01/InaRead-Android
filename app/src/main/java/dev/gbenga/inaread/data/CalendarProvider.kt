package dev.gbenga.inaread.data

import dev.gbenga.inaread.data.model.DateAndMonth
import dev.gbenga.inaread.domain.providers.CalendarProvider
import dev.gbenga.inaread.tokens.StringTokens
import dev.gbenga.inaread.utils.date.InaDateFormatter
import java.time.Month
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject



class CalendarProviderImpl @Inject constructor(
    private val inaDateFormatter: InaDateFormatter
    ) : CalendarProvider {

    override fun getDateAndMonth(): List<DateAndMonth> {
        val zoneId = ZoneId.of("GMT+1")
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(zoneId))
        val yearMonth = YearMonth.now(zoneId)

        val (startDay, endDay) = yearMonth
            .let { Pair(it.atDay(1)
                .dayOfMonth, it.atEndOfMonth().dayOfMonth) }
        calendar.set(yearMonth.year, yearMonth.monthValue, startDay)

        val dates = (startDay..endDay).map { dayOfMonth ->
            val monthName = Month.of(calendar.get(Calendar.MONTH)) // 1 = January
                .getDisplayName(TextStyle.FULL,
                    Locale.getDefault()).substring(0, 3)
            DateAndMonth(
                dayOfMonth,
                "${yearMonth.year}-${yearMonth.monthValue}-$dayOfMonth",
                yearMonth.monthValue,
                monthName,
                calendar.timeInMillis)
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

    override fun getCurrentDayOfMonth(): Int = Calendar
        .getInstance()
        .get(Calendar.DAY_OF_MONTH)

    override fun getFullDateFrom(dayOfMonth: Int): String {
        val zoneId = ZoneId.of("GMT+1")
        val yearMonth = YearMonth.now(zoneId)

        val monthName = Month.of(yearMonth.monthValue) // 1 = January
            .getDisplayName(TextStyle.FULL,
                Locale.getDefault())
        val currentYear = yearMonth.year
        return "$dayOfMonth of $monthName, $currentYear"
    }

    override fun getIndexOf(dayOfMonth: Int): Int {
        return getDateAndMonth().map { it.dayOfMonth }.indexOf(dayOfMonth)
    }

    override fun getDateTimeForImage(): String {
        return inaDateFormatter.dddMMMYyyyMhs(Date(System.currentTimeMillis()))
    }

    override fun getTodayYMD(): String {
        return inaDateFormatter.yyyyMMDD(Date(System.currentTimeMillis()))
    }

}