package dev.gbenga.inaread.ui.home

import dev.gbenga.inaread.ui.dashboard.DashboardScreen

sealed interface HomeEvent {
    data class SelectDay(val position: Int): HomeEvent
    object AddReading : HomeEvent
    object GotoSetting : HomeEvent
    object GotoHome : HomeEvent
    object LoadWeekDays: HomeEvent
    data object LoadMeterSummary : HomeEvent
    object LoadGreeting: HomeEvent
    object LoadTodaysDate: HomeEvent
}