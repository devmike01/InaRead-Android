package dev.gbenga.inaread.domain.date

import dev.gbenga.inaread.utils.date.DateAndMonth

interface CalendarProvider {

    fun getDateAndMonth(): List<DateAndMonth>

    fun getTodayInMillis(): Long

    fun getToday(): String

    fun greetBasedOnTime(): String

    fun getCurrentDayOfMonth(): Int

    fun getFullDateFrom(dayOfMonth: Int): String

    fun getIndexOf(dayOfMonth: Int): Int

}