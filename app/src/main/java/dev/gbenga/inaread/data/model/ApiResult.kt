package dev.gbenga.inaread.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResult<T>(val data: T? = null,
                        val error: String? = null,
    val status: Int = -1){

    val isSuccess = data != null
    val isError = error != null

}
