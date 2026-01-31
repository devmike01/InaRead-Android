package dev.gbenga.inaread.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.data.MeterSummaryRepositoryImpl
import dev.gbenga.inaread.data.db.UserDao
import dev.gbenga.inaread.data.network.AuthenticationService
import dev.gbenga.inaread.data.repository.AuthRepositoryImpl
import dev.gbenga.inaread.data.repository.MetricsRepositoryImpl
import dev.gbenga.inaread.data.repository.SettingsRepositoryImpl
import dev.gbenga.inaread.di.annotations.IOCoroutineContext
import dev.gbenga.inaread.domain.datastore.FakeUserDataStore
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.domain.repository.AllUnitUsageRepository
import dev.gbenga.inaread.domain.repository.AuthRepository
import dev.gbenga.inaread.domain.repository.MeterSummaryRepository
import dev.gbenga.inaread.domain.repository.MetricsRepository
import dev.gbenga.inaread.domain.repository.SettingsRepository
import dev.gbenga.inaread.domain.services.AllUnitUsageApiService
import dev.gbenga.inaread.domain.services.MeterSummaryApiService
import dev.gbenga.inaread.domain.services.ProfileApiService
import dev.gbenga.inaread.ui.metric.MetricsApiService
import dev.gbenga.inaread.ui.usage.AllUnitUsageRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMeterSummaryRepository(meterSummaryApiService: MeterSummaryApiService,
                                      userDataStore: UserDataStore): MeterSummaryRepository
    = MeterSummaryRepositoryImpl(meterSummaryApiService, userDataStore)


    @Provides
    fun provideMetricsRepository(
        @IOCoroutineContext ioContext: CoroutineDispatcher, userDataStore: UserDataStore,
        metricsApiService: MetricsApiService):
            MetricsRepository = MetricsRepositoryImpl(ioContext, userDataStore, metricsApiService)

    @Provides
    fun provideFakeUserDataStore(): UserDataStore{
        return FakeUserDataStore()
    }

    @Provides
    fun provideSettingsRepositoryImpl(profileApiService: ProfileApiService,
                                      userDataStore: UserDataStore,
                                      @IOCoroutineContext ioContext: CoroutineDispatcher): SettingsRepository {
        return SettingsRepositoryImpl(profileApiService, userDataStore, ioContext)
    }


    @Provides
    fun provideAllUnitUsageRepository(unitUsageApiService: AllUnitUsageApiService,
                                      userDataStore: UserDataStore,
                                      @IOCoroutineContext io: CoroutineDispatcher): AllUnitUsageRepository{
        return AllUnitUsageRepositoryImpl(

        unitUsageApiService, userDataStore, io)
    }


    @Provides
    fun provideAuthRepository(authApiService: AuthenticationService, userDao: UserDao,
                              @IOCoroutineContext io: CoroutineDispatcher)
    : AuthRepository = AuthRepositoryImpl(authApiService, userDao, io)
}

