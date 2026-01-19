package dev.gbenga.inaread.domain.providers

interface CoroutineProviders {

    suspend fun <T> withIOContext(dispatch: suspend () -> T): T
}