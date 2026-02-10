package dev.gbenga.inaread.data.datastore

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import dev.gbenga.inaread.domain.SecureAccessTokenStore

class AccessTokenStore(private val cryptoSharedPrefs: SharedPreferences) : SecureAccessTokenStore {

    companion object{
        private const val ACCESS_TOKEN = "AccessTokenStore.ACCESS_TOKEN";
    }

    override fun setAccessToken(jwt: String) {
        cryptoSharedPrefs.edit {
            putString(ACCESS_TOKEN, jwt)
        }
    }

    override fun getAccessToken(): String? {
        return cryptoSharedPrefs.getString(ACCESS_TOKEN, null)
    }
}
