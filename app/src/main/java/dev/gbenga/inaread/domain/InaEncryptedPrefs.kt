package dev.gbenga.inaread.domain


interface InaEncryptedPrefs {

    fun addString(key: String, value: String?)

    fun clear()

}