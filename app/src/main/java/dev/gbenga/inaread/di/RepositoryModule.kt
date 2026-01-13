package dev.gbenga.inaread.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.domain.meter.MeterSummaryRepository
import dev.gbenga.inaread.data.MeterSummaryRepositoryImpl
import dev.gbenga.inaread.data.datastore.UserDataStoreImpl
import dev.gbenga.inaread.domain.datastore.FakeUserDataStore
import dev.gbenga.inaread.domain.services.MeterSummaryApiService
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.ui.customs.dataStore
import dev.gbenga.inaread.utils.FakeMeterSummaryApiService

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMeterSummaryRepository(meterSummaryApiService: MeterSummaryApiService,
                                      userDataStore: UserDataStore): MeterSummaryRepository
    = MeterSummaryRepositoryImpl(meterSummaryApiService, userDataStore)

//    @Provides
//    fun provideUserDataStore(@ApplicationContext context: Context): UserDataStore {
//        return UserDataStoreImpl(context.dataStore)
//    }

    // Todo: To be removed
    @Provides
    fun provideMeterSummaryApiService(): MeterSummaryApiService{
        return FakeMeterSummaryApiService()
    }

    @Provides
    fun provideFakeUserDataStore(): UserDataStore{
        return FakeUserDataStore()
    }

}