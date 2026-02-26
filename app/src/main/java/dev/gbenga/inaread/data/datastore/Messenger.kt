package dev.gbenga.inaread.data.datastore

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion

class Messenger(private val dataStore : DataStore<Preferences>) {

    companion object{
        val MESSENGER = stringPreferencesKey("Messenger.MESSENGER")
    }

    suspend fun sendMessage(message: String){
        dataStore.edit { it[MESSENGER] = message }
    }

    suspend fun clearMessage(){
        dataStore.edit { it.remove(MESSENGER) }
    }

    fun receiveMessage(): Flow<String?>{
        //  dataStore.edit { it.remove(MESSENGER) }
        return dataStore.data.map {
            prefs -> prefs[MESSENGER]
        }
    }

}