package dev.gbenga.inaread.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    fun provideTestCoroutineScheduler(): TestCoroutineScheduler {
        return TestCoroutineScheduler()
    }

}