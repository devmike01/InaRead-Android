package dev.gbenga.inaread.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.domain.usescases.meter.MeterSummaryRepository
import dev.gbenga.inaread.data.MeterSummaryRepositoryImpl
import dev.gbenga.inaread.data.service.MeterSummaryApiService

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMeterSummaryRepository(meterSummaryApiService: MeterSummaryApiService): MeterSummaryRepository
    = MeterSummaryRepositoryImpl(meterSummaryApiService)
}