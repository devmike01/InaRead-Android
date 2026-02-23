package dev.gbenga.inaread.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.data.db.UserDao
import dev.gbenga.inaread.domain.SecureAccessTokenStore
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.utils.UserProvider
import dev.gbenga.inaread.utils.UserProviderImpl

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    fun provideUserProvider(userDataStore: UserDataStore,
                            userDao: UserDao,
                            accessTokenStore: SecureAccessTokenStore): UserProvider {
        return UserProviderImpl(userDataStore, userDao,
            accessTokenStore);
    }
}