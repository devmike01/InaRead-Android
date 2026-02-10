package dev.gbenga.inaread.domain.datastore

interface InaEncryptedPrefs {

    fun addString(key: String, value: String?)

    fun getString(key: String): String?

    fun clear()

}