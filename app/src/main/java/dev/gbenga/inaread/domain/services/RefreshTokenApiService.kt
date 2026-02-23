package dev.gbenga.inaread.domain.services

import dev.gbenga.inaread.BuildConfig
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.RefreshTokenData
import dev.gbenga.inaread.data.model.RefreshTokenRequest
import dev.gbenga.inaread.data.network.EndPoints
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import retrofit2.http.Body
import retrofit2.http.GET
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


interface RefreshTokenApiService {

    @GET(EndPoints.RefreshToken)
    suspend fun refreshToken(@Body request: RefreshTokenRequest): ApiResult<RefreshTokenData>
}

class RefreshTokenApiServiceImpl(): RefreshTokenApiService{

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun refreshToken(
        request: RefreshTokenRequest
    ): ApiResult<RefreshTokenData> {

        val url = URL(BuildConfig.BASE_URL + EndPoints.RefreshToken)
        val conn = (url.openConnection() as HttpURLConnection)

        return try {
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8")
            conn.setRequestProperty("Accept", "application/json")
            conn.doOutput = true
            conn.doInput = true

            // Write request body
            val jsonBody = Json.encodeToString(request)
            conn.outputStream.use { os ->
                os.write(jsonBody.toByteArray())
            }

            conn.inputStream.readJsonFromStream()

        } catch (e: Exception) {
            ApiResult(null, e.message ?: "Unknown error")
        } finally {
            conn.disconnect()
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun InputStream.readJsonFromStream(): ApiResult<RefreshTokenData>{
        return use { stream ->
            Json.decodeFromStream(stream)
        }
    }
}