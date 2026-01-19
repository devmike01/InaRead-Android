package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.data.model.ProfileResponse

interface ProfileApiService {

    suspend fun getProfile(): ProfileResponse

}