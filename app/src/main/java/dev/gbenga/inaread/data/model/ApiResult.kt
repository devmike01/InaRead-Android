package dev.gbenga.inaread.data.model

data class ApiResult<T>(val data: T,
                        val error: String)