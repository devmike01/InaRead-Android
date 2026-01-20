package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.SettingsMenu
import dev.gbenga.inaread.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsMenuUseCase  @Inject constructor(private val settingsRepository: SettingsRepository){

    operator fun invoke(): List<SettingsMenu> = settingsRepository.getSettingsMenu()
}