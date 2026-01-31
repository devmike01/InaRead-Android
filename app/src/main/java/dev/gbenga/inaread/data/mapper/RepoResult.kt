package dev.gbenga.inaread.data.mapper

sealed interface RepoResult<out T> {
    data class Success<T>(val data: T): RepoResult<T>
    data class Error(val message: String): RepoResult<Nothing>
}