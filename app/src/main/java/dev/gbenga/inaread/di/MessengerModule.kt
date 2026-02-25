package dev.gbenga.inaread.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.gbenga.inaread.data.datastore.Messenger
import dev.gbenga.inaread.ui.customs.dataStore

@Module
@InstallIn(ViewModelComponent::class)
class MessengerModule {

    @Provides
    fun provideMessenger(@ApplicationContext context: Context): Messenger {
        return Messenger(context.dataStore)
    }
}