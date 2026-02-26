package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.db.entities.UserEntity
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.SettingsMenu

interface SettingsRepository {

    suspend fun getUserProfile(): RepoResult<UserEntity>

    fun getSettingsMenu(): List<SettingsMenu>
}