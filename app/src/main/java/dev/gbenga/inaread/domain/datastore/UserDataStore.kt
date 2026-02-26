package dev.gbenga.inaread.domain.datastore

import dev.gbenga.inaread.data.db.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface UserDataStore {
    suspend fun setProfileId(profileId: String)
    fun getProfileId(): Flow<String?>
    suspend fun removeUserProfileId()
}