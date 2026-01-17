package dev.gbenga.inaread.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.data.model.ProfileResponse
import dev.gbenga.inaread.domain.services.MeterSummaryApiService
import dev.gbenga.inaread.domain.settings.ProfileApiService
import dev.gbenga.inaread.domain.settings.SettingsRepository
import dev.gbenga.inaread.ui.metric.MetricsApiService
import dev.gbenga.inaread.ui.settings.SettingsRepositoryImpl
import dev.gbenga.inaread.utils.FakeMeterSummaryApiService
import dev.gbenga.inaread.utils.FakeMetricsApiService


@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {


    // Todo: To be removed
    @Provides
    fun provideMeterSummaryApiService(): MeterSummaryApiService{
        return FakeMeterSummaryApiService()
    }

    @Provides
    fun provideMetricsApiService(): MetricsApiService{
        return FakeMetricsApiService()
    }

    @Provides
    fun provideSettingsRepository(): ProfileApiService{
        return object : ProfileApiService{
            override suspend fun getProfile(): ProfileResponse {
                return ProfileResponse("Devmike01", "devmike01@gbenga.com", userId = "ffmg")
            }

        }
    }
}