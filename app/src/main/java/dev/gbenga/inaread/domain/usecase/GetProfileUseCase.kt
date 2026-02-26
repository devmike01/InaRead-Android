package dev.gbenga.inaread.domain.usecase

import dev.gbenga.inaread.data.db.entities.UserEntity
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.Profile
import dev.gbenga.inaread.domain.repository.SettingsRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {

    suspend operator fun invoke(): RepoResult<UserEntity> = settingsRepository.getUserProfile()
}