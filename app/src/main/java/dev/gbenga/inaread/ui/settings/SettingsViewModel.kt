package dev.gbenga.inaread.ui.settings

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.domain.usecase.GetProfileUseCase
import dev.gbenga.inaread.domain.usecase.GetSettingsMenuUseCase
import dev.gbenga.inaread.ui.home.VectorInaTextIcon
import dev.gbenga.inaread.utils.InaReadViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val getProfileUseCase: GetProfileUseCase,
                                            private val getSettingsMenuUseCase: GetSettingsMenuUseCase) : InaReadViewModel<SettingsUiState,
        SettingsEvent>(SettingsUiState()) {

            val profile = state.map { it.profile }.distinctUntilChanged()
    val menuItems = state.map { it.settingMenu }.distinctUntilChanged()



    fun populateSettings(){
        viewModelScope.launch {
            val profile = getProfileUseCase.invoke()
            setState { it.copy(profile = profile,
                settingMenu = getSettingsMenuUseCase.invoke().map { menu ->
                    VectorInaTextIcon(
                        null, menu.value ?: "",
                        menu.name, 0xFFFFFFFF
                    )
                }) }
        }
    }

    fun logOut(){
        sendEvent(SettingsEvent.LogOut)
    }
}