package dev.gbenga.inaread.utils

import dev.gbenga.inaread.utils.nav.InaScreen


sealed interface NavigationEvent{

    data class Navigate(val screen: InaScreen): NavigationEvent

    object NavigateBack: NavigationEvent
}