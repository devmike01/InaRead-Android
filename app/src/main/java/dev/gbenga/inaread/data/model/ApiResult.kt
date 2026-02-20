package dev.gbenga.inaread.data.model

import dev.gbenga.inaread.tokens.StringTokens

data class ApiResult<T>(val data: T? = null,
                        val error: String? = null,
    val status: Int = -1){

    val isSuccess = data != null
    val isError = error != null

}
