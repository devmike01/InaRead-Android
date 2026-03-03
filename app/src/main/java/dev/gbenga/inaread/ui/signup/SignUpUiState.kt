package dev.gbenga.inaread.ui.signup

import dev.gbenga.inaread.ui.dropdown.country.CountryData
import dev.gbenga.inaread.ui.home.UiData
import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.UiState
import dev.gbenga.inaread.utils.UiStateWithIdle

data class SignUpUiState(
    val signUp: UiStateWithIdle<String> = UiStateWithIdle.Idle,
    val meterCategory: UiState<List<MeterCategory>> = UiState.Loading,
    val meterTypes: UiState<List<String>> = UiState.Loading,
    val countries: UiState<List<CountryData>> = UiState.Loading,
    val enabledSubmit: Boolean = false
    ): InaReadUiState

