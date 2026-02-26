package dev.gbenga.inaread.ui.settings

import dev.gbenga.inaread.data.model.Profile
import dev.gbenga.inaread.ui.home.VectorInaTextIcon
import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle

data class SettingsUiState(val profile: UiState<Profile> = UiState.Loading,
                           val settingMenu: List<VectorInaTextIcon> = emptyList()): InaReadUiState

