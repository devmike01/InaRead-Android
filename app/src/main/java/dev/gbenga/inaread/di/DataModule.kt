package dev.gbenga.inaread.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.domain.date.CalendarProvider
import dev.gbenga.inaread.data.CalendarProviderImpl
import dev.gbenga.inaread.utils.date.InaDateFormatter
import java.text.SimpleDateFormat
import java.util.Locale

@InstallIn(SingletonComponent::class)
@Module
object UtilModule {


    @Provides
    fun provideSimpleDateFormat() : SimpleDateFormat = SimpleDateFormat("", Locale.US)


    @Provides
    fun provideCalendarProvider(inaDateFormatter: InaDateFormatter):
            CalendarProvider = CalendarProviderImpl(inaDateFormatter)
}