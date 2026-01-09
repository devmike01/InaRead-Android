package dev.gbenga.inaread.ui.home

import dev.gbenga.inaread.utils.InaReadEvent

sealed interface HomeEvent : InaReadEvent {
    data class SelectMonth(val position: Int): HomeEvent
    object AddReading : HomeEvent
    object GotoSetting : HomeEvent
    object GotoHome : HomeEvent
}