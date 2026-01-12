package dev.gbenga.inaread.domain.usescases.coroutines

interface CoroutineProviders {

    suspend fun <T> withIOContext(dispatch: suspend () -> T): T
}