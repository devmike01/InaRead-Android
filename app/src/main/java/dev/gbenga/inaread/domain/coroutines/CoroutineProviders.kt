package dev.gbenga.inaread.domain.coroutines

interface CoroutineProviders {

    suspend fun <T> withIOContext(dispatch: suspend () -> T): T
}