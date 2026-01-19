package dev.gbenga.inaread.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.domain.providers.CalendarProvider
import dev.gbenga.inaread.data.CalendarProviderImpl
import dev.gbenga.inaread.data.ImagePickerProviderImpl
import dev.gbenga.inaread.di.annotations.IOCoroutineContext
import dev.gbenga.inaread.domain.providers.ImagePickerProvider
import dev.gbenga.inaread.utils.date.InaDateFormatter
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.coroutines.CoroutineContext

@InstallIn(SingletonComponent::class)
@Module
object DataModule {


    @Provides
    fun provideSimpleDateFormat() : SimpleDateFormat = SimpleDateFormat("", Locale.US)


    @Provides
    fun provideCalendarProvider(inaDateFormatter: InaDateFormatter):
            CalendarProvider = CalendarProviderImpl(inaDateFormatter)

    @Provides
    fun provideImagePickerProvider(@ApplicationContext context: Context,
                                   @IOCoroutineContext ioDispatcher : CoroutineContext,): ImagePickerProvider
    = ImagePickerProviderImpl(context, ioDispatcher)
}