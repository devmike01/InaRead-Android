package dev.gbenga.inaread.ui.home

@Deprecated("Marked for removal because of internal loop in viewmodel")

sealed interface HomeEvent {
    data class SelectDay(val dayOfMonth: Int, val selectedPos: Int): HomeEvent
    object AddReading : HomeEvent
    object GotoSetting : HomeEvent
    object GotoHome : HomeEvent
    object LoadWeekDays: HomeEvent
    data class LoadMeterSummary(val fromDayOfMonth: Int) : HomeEvent
    object LoadGreeting: HomeEvent
    object LoadTodaysDate: HomeEvent
}