package dev.gbenga.inaread.utils

sealed interface UiState<out T>{
    data object Loading : UiState<Nothing>
    data class Error(val message: String): UiState<Nothing>
    data class Success<T>(val data: T): UiState<T>

}



sealed interface UiStateWithIdle<out T>{
    data object Loading : UiStateWithIdle<Nothing>
    object Idle: UiStateWithIdle<Nothing>
    data class Error(val message: String): UiStateWithIdle<Nothing>
    data class Success<T>(val data: T): UiStateWithIdle<T>

}