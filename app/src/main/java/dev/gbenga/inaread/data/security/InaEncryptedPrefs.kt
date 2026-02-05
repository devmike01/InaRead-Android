package dev.gbenga.inaread.data.security

import android.content.SharedPreferences
import androidx.core.content.edit
import dev.gbenga.inaread.domain.InaEncryptedPrefs


class InaEncryptedPrefsImpl(private val preferences: SharedPreferences) : InaEncryptedPrefs {


    override fun addString(key: String, value: String?) {
        if (value == null) throw NullPointerException("$value cannot be persisted")
        preferences.edit {
            putString(key, value)
        }
    }

    override fun clear() {
        preferences.edit { clear() }
    }

}