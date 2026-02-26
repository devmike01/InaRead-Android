package dev.gbenga.inaread.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    fun provideDateTimeFormatter(): DateTimeFormatter{
        return DateTimeFormatter.ISO_INSTANT
    }
}