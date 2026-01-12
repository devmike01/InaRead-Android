package dev.gbenga.inaread.ui.home

sealed interface HomeEvent {
    data class SelectDay(val position: Int): HomeEvent
    object AddReading : HomeEvent
    object GotoSetting : HomeEvent
    object GotoHome : HomeEvent
    object LoadWeekDays: HomeEvent
    data object LoadMeterSummary : HomeEvent
}