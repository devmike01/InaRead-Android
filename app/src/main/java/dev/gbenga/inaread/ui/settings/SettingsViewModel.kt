package dev.gbenga.inaread.ui.settings

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.domain.usecase.GetProfileUseCase
import dev.gbenga.inaread.domain.usecase.GetSettingsMenuUseCase
import dev.gbenga.inaread.domain.usecase.SignOutUseCase
import dev.gbenga.inaread.ui.customs.uiStateWithIdleRunCatching
import dev.gbenga.inaread.ui.home.VectorInaTextIcon
import dev.gbenga.inaread.utils.InaReadViewModel
import dev.gbenga.inaread.utils.InaReadViewModelV2
import dev.gbenga.inaread.utils.NavigationEvent
import dev.gbenga.inaread.utils.nav.InaScreen
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val getProfileUseCase: GetProfileUseCase,
                                            private val getSettingsMenuUseCase: GetSettingsMenuUseCase,
    private val signOutUseCase: SignOutUseCase)
    : InaReadViewModelV2<SettingsUiState>(SettingsUiState()) {

     //       val profile = state.map { it.profile }.distinctUntilChanged()
    val menuItems = state.map { it.settingMenu }.distinctUntilChanged()


    fun populateSettings(){
        viewModelScope.launch {
            val profile = uiStateWithIdleRunCatching { getProfileUseCase() }
            val settingsMenu = getSettingsMenuUseCase().map { menu ->
                VectorInaTextIcon(
                    null, menu.value ?: "",
                    menu.name, 0xFFFFFFFF,
                    key = menu.key
                )
            }
            setState { it.copy(
                profile = profile,
                settingMenu = settingsMenu) }
        }
    }

    fun logOut(){
        viewModelScope.launch {
            showLoading()
            when(val repoState = signOutUseCase()){
                is RepoResult.Success -> {
                    hideLoading()
                    navigate(NavigationEvent.NavigateTaskTop(InaScreen.Login))
                }
                is RepoResult.Error -> {
                    hideLoading()
                    showUiMessage(repoState.message)
                }
            }
        }
    }
}