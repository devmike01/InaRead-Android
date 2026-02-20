package dev.gbenga.inaread.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.utils.UserProvider
import dev.gbenga.inaread.utils.UserProviderImpl

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    fun provideUserProvider(userDataStore: UserDataStore): UserProvider {
        return UserProviderImpl(userDataStore);
    }
}