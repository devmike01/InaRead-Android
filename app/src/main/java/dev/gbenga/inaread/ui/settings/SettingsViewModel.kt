package dev.gbenga.inaread.ui.settings

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.gbenga.inaread.domain.settings.GetProfileUseCase
import dev.gbenga.inaread.domain.settings.GetSettingsMenuUseCase
import dev.gbenga.inaread.ui.home.VectorInaTextIcon
import dev.gbenga.inaread.utils.InaReadViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val getProfileUseCase: GetProfileUseCase,
                                            private val getSettingsMenuUseCase: GetSettingsMenuUseCase) : InaReadViewModel<SettingsUiState,
        SettingsEvent>(SettingsUiState()) {

    override fun watchEvents() {
        viewModelScope.launch {
            events.collect { uiEvent ->
                when(uiEvent){
                    is SettingsEvent.LoadProfile -> {
                        val profile = getProfileUseCase.invoke()
                        setState { it.copy(profile = profile) }
                    }
                    is SettingsEvent.LogOut -> {

                    }
                    is SettingsEvent.LoadMenu -> {
                        setState { it.copy(
                            settingMenu = getSettingsMenuUseCase.invoke().map { menu ->
                                VectorInaTextIcon(
                                    null, menu.value ?: "",
                                    menu.name, 0xFFFFFFFF
                                )
                            }
                        ) }
                    }
                }
            }
        }
    }



    fun populateSettings(){
        sendEvent(SettingsEvent.LoadProfile)
        sendEvent(SettingsEvent.LoadMenu)
    }

    fun logOut(){
        sendEvent(SettingsEvent.LogOut)
    }
}