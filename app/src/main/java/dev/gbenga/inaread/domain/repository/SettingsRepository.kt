package dev.gbenga.inaread.domain.repository

import dev.gbenga.inaread.data.model.ProfileResponse
import dev.gbenga.inaread.data.model.SettingsMenu

interface SettingsRepository {

    suspend fun getUserProfile(): ProfileResponse

    fun getSettingsMenu(): List<SettingsMenu>
}