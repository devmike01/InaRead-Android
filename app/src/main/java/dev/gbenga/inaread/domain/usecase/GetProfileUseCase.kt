package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.model.Profile
import dev.gbenga.inaread.domain.repository.SettingsRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(): Profile = settingsRepository.getUserProfile().let { profile ->
        Profile(
            username = profile.username,
            email=profile.email,
            initial = profile.username[0].uppercase()
        )
    }
}