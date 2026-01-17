package dev.gbenga.inaread.ui.settings

sealed interface SettingsEvent {
    object LoadProfile: SettingsEvent
    object LoadMenu: SettingsEvent
    object LogOut: SettingsEvent
}