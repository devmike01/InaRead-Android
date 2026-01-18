package dev.gbenga.inaread.ui.settings

@Deprecated("Marked for removal because of internal loop in viewmodel")

sealed interface SettingsEvent {
    object LoadProfile: SettingsEvent
    object LoadMenu: SettingsEvent
    object LogOut: SettingsEvent
}