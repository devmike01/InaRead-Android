package dev.gbenga.inaread.ui.signup

import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.UiStateWithIdle

data class SignUpUiState(
    val signUp: UiStateWithIdle<String> = UiStateWithIdle.Idle,
    ): InaReadUiState

