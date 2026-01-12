package dev.gbenga.inaread.domain.usescases.datastore

import dev.gbenga.inaread.data.model.ProfileResponse
import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    fun setProfile(profile: ProfileResponse)
    fun getProfile(): Flow<ProfileResponse>
}