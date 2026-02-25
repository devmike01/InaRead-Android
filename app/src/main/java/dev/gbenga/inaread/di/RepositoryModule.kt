package dev.gbenga.inaread.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.data.MeterUsageRepositoryImpl
import dev.gbenga.inaread.data.datastore.UserDataStoreImpl
import dev.gbenga.inaread.data.db.PowerUsageSummaryDao
import dev.gbenga.inaread.data.db.UserDao
import dev.gbenga.inaread.data.mapper.RepoResult
import dev.gbenga.inaread.data.model.ApplianceResponse
import dev.gbenga.inaread.data.model.ApplianceResponseData
import dev.gbenga.inaread.data.model.AppliancesRequest
import dev.gbenga.inaread.domain.services.AuthenticationApiService
import dev.gbenga.inaread.domain.services.MeterUsageStatisticService
import dev.gbenga.inaread.data.repository.AllUnitUsageRepository
import dev.gbenga.inaread.data.repository.AuthRepositoryImpl
import dev.gbenga.inaread.data.repository.SettingsRepositoryImpl
import dev.gbenga.inaread.di.annotations.IOCoroutineContext
import dev.gbenga.inaread.domain.datastore.UserDataStore
import dev.gbenga.inaread.domain.repository.AppliancesRepository
import dev.gbenga.inaread.domain.repository.AuthRepository
import dev.gbenga.inaread.domain.repository.MeterUsageRepository
import dev.gbenga.inaread.domain.repository.SettingsRepository
import dev.gbenga.inaread.domain.services.AllUsageApiService
import dev.gbenga.inaread.domain.services.ProfileApiService
import dev.gbenga.inaread.ui.customs.dataStore
import dev.gbenga.inaread.ui.usage.AllUnitUsageRepositoryImpl
import dev.gbenga.inaread.utils.UserProvider
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMeterSummaryRepository(meterUsageApiService: MeterUsageStatisticService,
                                      userProvider: UserProvider,
                                      powerUsageSummaryDao: PowerUsageSummaryDao,): MeterUsageRepository
    = MeterUsageRepositoryImpl(meterUsageApiService, userProvider,
        powerUsageSummaryDao)


//    @Provides
//    fun provideMetricsRepository(
//        @IOCoroutineContext ioContext: CoroutineDispatcher, userDataStore: UserDataStore,
//        metricsApiService: MetricsApiService):
//            MetricsRepository = MetricsRepositoryImpl(ioContext, userDataStore, metricsApiService)

    @Provides
    fun provideUserDataStoreImpl(@ApplicationContext context: Context): UserDataStore{
        return UserDataStoreImpl(context.dataStore)
    }

    @Provides
    fun provideSettingsRepositoryImpl(profileApiService: ProfileApiService,
                                      userDataStore: UserDataStore,
                                      @IOCoroutineContext ioContext: CoroutineDispatcher): SettingsRepository {
        return SettingsRepositoryImpl(profileApiService, userDataStore, ioContext)
    }


    @Provides
    fun provideAllUnitUsageRepository(applianceApiService: AllUsageApiService,
                                        userProvider: UserProvider,
                                        @IOCoroutineContext io: CoroutineDispatcher): AllUnitUsageRepository{
        return AllUnitUsageRepositoryImpl(

            applianceApiService, userProvider, io
        )
    }

    @Provides
    fun provideAppliancesRepository(): AppliancesRepository {
        return object : AppliancesRepository{
            override suspend fun executeGetAppliances(): RepoResult<ApplianceResponse> {
                TODO("Not yet implemented")
            }

            override suspend fun executeAddAppliance(request: AppliancesRequest): RepoResult<ApplianceResponseData> {
                TODO("Not yet implemented")
            }

        }
    }

    @Provides
    fun provideAuthRepository(authApiService: AuthenticationApiService, userDao: UserDao,
                              userProvider: UserProvider,
                              @IOCoroutineContext io: CoroutineDispatcher)
    : AuthRepository = AuthRepositoryImpl(
        authApiService, userDao,
        userProvider,
        io)


}

