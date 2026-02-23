package dev.gbenga.inaread.data.datastore

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import dev.gbenga.inaread.domain.SecureAccessTokenStore

class AccessTokenStore(private val cryptoSharedPrefs: SharedPreferences) : SecureAccessTokenStore {

    companion object{
        private const val ACCESS_TOKEN = "AccessTokenStore.ACCESS_TOKEN";
        private const val REFRESH_TOKEN = "AccessTokenStore.REFRESH_TOKEN"
    }

    override fun setAccessToken(jwt: String) {
        cryptoSharedPrefs.edit {
            putString(ACCESS_TOKEN, jwt)
        }
    }

    override fun getAccessToken(): String? {
        return cryptoSharedPrefs.getString(ACCESS_TOKEN, null)
    }

    override fun getRefreshToken(): String? {
        return cryptoSharedPrefs.getString(REFRESH_TOKEN, null)
    }

    override fun setRefreshToken(token: String) {
        cryptoSharedPrefs.edit {
            putString(REFRESH_TOKEN, token)
        }
    }

    override fun removeTokens() {
        cryptoSharedPrefs.edit{
            remove(REFRESH_TOKEN)
            remove(ACCESS_TOKEN)
        }
    }
}
