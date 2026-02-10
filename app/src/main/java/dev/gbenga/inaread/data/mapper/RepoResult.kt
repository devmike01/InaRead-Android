package dev.gbenga.inaread.data.mapper

import android.util.Log
import dev.gbenga.inaread.tokens.StringTokens

sealed interface RepoResult<out T> {
    data class Success<T>(val data: T): RepoResult<T>
    data class Error(val message: String): RepoResult<Nothing>

}

//inline fun <T, R> RepoResult<T>.map(): RepoResult<R>{
//
//}

inline fun <T, R> RepoResult<T>.map(
    onMap: (T) -> R): RepoResult<R>{
     return when(this){
        is RepoResult.Error -> {
            RepoResult.Error(message)
        }
        is RepoResult.Success<T> -> RepoResult.Success(onMap(this.data))
    }
}

