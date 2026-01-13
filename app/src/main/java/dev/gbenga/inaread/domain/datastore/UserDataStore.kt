package dev.gbenga.inaread.domain.datastore

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface UserDataStore {
    suspend fun setProfileId(profileId: String)
    fun getProfileId(): Flow<String?>
}

class FakeUserDataStore : UserDataStore{

    override suspend fun setProfileId(profileId: String) {
        TODO("Not yet implemented")
    }

    override fun getProfileId(): Flow<String?> = flowOf("21")

}