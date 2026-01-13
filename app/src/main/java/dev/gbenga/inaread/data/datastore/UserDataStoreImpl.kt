package dev.gbenga.inaread.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.gbenga.inaread.domain.datastore.UserDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreImpl(private val profileDataStore : DataStore<Preferences>) : UserDataStore {

    companion object{
        val USER_ID_STORE = stringPreferencesKey("UserDataStoreImpl.USER_ID_STORE")
    }

    override suspend fun setProfileId(profileId: String) {
        profileDataStore.updateData {
            it.toMutablePreferences().also { prefs ->
                prefs[USER_ID_STORE] = profileId
            }
        }
    }

    override fun getProfileId(): Flow<String?> = profileDataStore.data.map { prefs ->
        prefs[USER_ID_STORE]
    }

}