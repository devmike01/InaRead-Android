package dev.gbenga.inaread.domain.settings

import dev.gbenga.inaread.data.model.Profile
import dev.gbenga.inaread.data.model.ProfileResponse
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend fun invoke(): Profile = settingsRepository.getUserProfile().let { profile ->
        Profile(
            username = profile.username,
            email=profile.email,
            initial = profile.username[0].uppercase()
        )
    }
}