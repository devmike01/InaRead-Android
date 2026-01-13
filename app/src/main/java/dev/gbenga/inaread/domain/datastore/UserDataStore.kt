package dev.gbenga.inaread.domain.usescases.datastore

import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    suspend fun setProfileId(profileId: String)
    fun getProfileId(): Flow<String?>
}