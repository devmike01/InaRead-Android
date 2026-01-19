package dev.gbenga.inaread.data.model

data class ApiResult<T>(val data: T? = null,
                        val error: String? = null){

    val isSuccess = data != null
    val isError = error != null
}