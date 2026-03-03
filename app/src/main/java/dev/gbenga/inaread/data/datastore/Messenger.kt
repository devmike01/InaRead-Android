package dev.gbenga.inaread.data.datastore

import android.content.SharedPreferences
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class Messenger(private val dataStore : DataStore<Preferences>) {

    companion object{
        val MESSENGER = stringPreferencesKey("Messenger.MESSENGER")
    }

    suspend fun sendMessage(message: String){
        dataStore.edit { it[MESSENGER] = message }
    }

    suspend fun clearMessage(io: CoroutineContext = Dispatchers.IO){
        withContext(io){
            dataStore.edit { it.remove(MESSENGER) }
        }
    }

    suspend fun receiveMessage(io: CoroutineContext = Dispatchers.IO): Flow<String?>{
        //  dataStore.edit { it.remove(MESSENGER) }
        return withContext(io){
            dataStore.data.map {
                    prefs -> prefs[MESSENGER]
            }.onCompletion {
                Log.i("receiveMessage", "completed")
            }
        }
    }

}