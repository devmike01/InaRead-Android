package dev.gbenga.inaread.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.di.annotations.IOCoroutineContext
import dev.gbenga.inaread.di.annotations.IOCoroutineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    @IOCoroutineContext
    fun provideIO() : CoroutineContext = Dispatchers.IO

    @Provides
    @IOCoroutineScope
    fun provideIOCoroutine(): CoroutineScope{
        return CoroutineScope(Dispatchers.IO)
    }

}