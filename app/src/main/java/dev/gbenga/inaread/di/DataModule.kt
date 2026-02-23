package dev.gbenga.inaread.di

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.data.CalendarProviderImpl
import dev.gbenga.inaread.data.ImagePickerProviderImpl
import dev.gbenga.inaread.data.datastore.AccessTokenStore
import dev.gbenga.inaread.data.db.InaReadDatabase
import dev.gbenga.inaread.data.db.PowerUsageSummaryDao
import dev.gbenga.inaread.data.db.UserDao
import dev.gbenga.inaread.data.security.InaEncryptedPrefsImpl
import dev.gbenga.inaread.di.annotations.EncryptedSharedPrefs
import dev.gbenga.inaread.di.annotations.IOCoroutineContext
import dev.gbenga.inaread.domain.datastore.InaEncryptedPrefs
import dev.gbenga.inaread.domain.providers.CalendarProvider
import dev.gbenga.inaread.domain.providers.ImagePickerProvider
import dev.gbenga.inaread.utils.date.InaDateFormatter
import kotlinx.coroutines.CoroutineDispatcher
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Properties

@InstallIn(SingletonComponent::class)
@Module
object DataModule {


    @Provides
    fun provideInaReadDatabase(@ApplicationContext context: Context): InaReadDatabase{
        return Room.databaseBuilder(context,
            InaReadDatabase::class.java,
            "inaread-db")
            .fallbackToDestructiveMigration(true) // TODO:
            .build()
    }

    @Provides
    fun provideUserDao(db: InaReadDatabase): UserDao{
        return db.userDao()
    }

    @Provides
    fun providePowerUsageSummaryDao(db: InaReadDatabase): PowerUsageSummaryDao {
        return db.powerUsageSummary()
    }

    @Provides
    fun provideSimpleDateFormat() : SimpleDateFormat = SimpleDateFormat("", Locale.US)


    @Provides
    fun provideCalendarProvider(inaDateFormatter: InaDateFormatter):
            CalendarProvider = CalendarProviderImpl(inaDateFormatter)

    @Provides
    fun provideImagePickerProvider(@ApplicationContext context: Context,
                                   @IOCoroutineContext ioDispatcher : CoroutineDispatcher,): ImagePickerProvider
    = ImagePickerProviderImpl(context, ioDispatcher)

    @Provides
    fun provideProperties(@ApplicationContext context: Context): Properties{
        val properties = Properties()
        val ins = context.assets.open("keys.properties")
        properties.load(ins)
        return properties
    }

    @Provides
    fun provideMasterKey(@ApplicationContext context: Context, properties: Properties): MasterKey? {

         try {
            val spec = KeyGenParameterSpec.Builder(
                "_androidx_security_master_key_",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build()

            return MasterKey.Builder(context)
                .setKeyGenParameterSpec(spec)
                .build()
        } catch (e: Exception) {
            Log.e(javaClass.getSimpleName(), "Error on getting master key", e)
        }
        return null
    }

    /**
     * Try to create a secure shared preferences but fall back to
     * ordinary shared preferences if masterkey is null
     */
    @Provides
    @EncryptedSharedPrefs
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context, masterKey: MasterKey?): SharedPreferences{
        return masterKey?.let { key ->
            EncryptedSharedPreferences.create(context,"access_token",
                key,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences
                    .PrefValueEncryptionScheme.AES256_GCM)
        } ?: context.getSharedPreferences("access_token", Context.MODE_PRIVATE)

    }

    @Provides
    fun provideAccessTokenStore(@EncryptedSharedPrefs prefs: SharedPreferences): AccessTokenStore {
        return AccessTokenStore(prefs)
    }

    @Provides
    fun provideInaEncryptedPrefs(@EncryptedSharedPrefs preferences: SharedPreferences): InaEncryptedPrefs {
        return InaEncryptedPrefsImpl(preferences)
    }
}