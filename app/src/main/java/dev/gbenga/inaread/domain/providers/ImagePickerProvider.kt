package dev.gbenga.inaread.domain.providers

import android.net.Uri

interface ImagePickerProvider {

    fun uriToBytes(uri: Uri): ByteArray?

    suspend fun getAbsolutePathFor(uri: Uri): String
}