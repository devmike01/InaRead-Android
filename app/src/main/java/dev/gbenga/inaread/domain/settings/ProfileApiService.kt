package dev.gbenga.inaread.domain.settings

import dev.gbenga.inaread.data.model.ProfileResponse

interface ProfileApiService {

    suspend fun getProfile(): ProfileResponse

}