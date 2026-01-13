package dev.gbenga.inaread.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import dev.gbenga.inaread.data.model.ProfileResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object ProfileResponseSerializer : Serializer<ProfileResponse> {

    override val defaultValue: ProfileResponse = ProfileResponse(
        userId = "",
        email = "",
        username = ""
    )

    override suspend fun readFrom(input: InputStream): ProfileResponse =
        try {
            Json.decodeFromString<ProfileResponse>(
                input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read Settings", serialization)
        }

    override suspend fun writeTo(t: ProfileResponse, output: OutputStream) {
        output.write(
            Json.encodeToString(t)
                .encodeToByteArray()
        )
    }
}
