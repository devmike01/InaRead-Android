package dev.gbenga.inaread.data.datastore

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import dev.gbenga.inaread.domain.SecureAccessTokenStore

class AccessTokenStore(private val cryptoSharedPrefs: EncryptedSharedPreferences) : SecureAccessTokenStore {

    override fun setAccessToken(jwt: String) {

    }

    override fun getAccessToken(): String {
        TODO("Not yet implemented")
    }
}