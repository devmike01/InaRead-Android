package dev.gbenga.inaread.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {

    @Provides
    fun provideIO() : CoroutineDispatcher = Dispatchers.IO

    @Provides
    @IOCoroutineScope
    fun provideIOCoroutine(): CoroutineScope{
        return CoroutineScope(Dispatchers.IO)
    }

}