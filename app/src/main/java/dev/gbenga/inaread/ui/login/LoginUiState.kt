package dev.gbenga.inaread.ui.login

import dev.gbenga.inaread.data.auth.UiLogin
import dev.gbenga.inaread.utils.InaReadUiState
import dev.gbenga.inaread.utils.UiStateWithIdle
import dev.gbenga.inaread.utils.nav.InaScreen

data class LoginUiState(
    val login: UiStateWithIdle<UiLogin> = UiStateWithIdle.Idle,
    ): InaReadUiState {

    companion object{
        val Empty = LoginUiState()
    }
}
