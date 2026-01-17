package dev.gbenga.inaread.ui.settings

import dev.gbenga.inaread.data.model.Profile
import dev.gbenga.inaread.data.model.SettingsMenu
import dev.gbenga.inaread.ui.home.VectorInaTextIcon
import dev.gbenga.inaread.utils.InaReadUiState

data class SettingsUiState(val profile: Profile = Profile(),
     val settingMenu: List<VectorInaTextIcon> = emptyList()): InaReadUiState