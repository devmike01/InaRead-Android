package dev.gbenga.inaread.ui.home

sealed interface UiData<out T>{
    data class Content<T>(val data: T): UiData<T>
    object EmptyContent: UiData<Nothing>
}