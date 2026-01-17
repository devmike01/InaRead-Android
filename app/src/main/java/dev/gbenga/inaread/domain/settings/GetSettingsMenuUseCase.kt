package dev.gbenga.inaread.domain.settings

import dev.gbenga.inaread.data.model.SettingsMenu
import javax.inject.Inject

class GetSettingsMenuUseCase  @Inject constructor(private val settingsRepository: SettingsRepository){

    fun invoke(): List<SettingsMenu> = settingsRepository.getSettingsMenu()
}