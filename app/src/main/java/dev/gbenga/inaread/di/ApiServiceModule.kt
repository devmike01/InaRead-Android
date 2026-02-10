package dev.gbenga.inaread.di

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.gbenga.inaread.BuildConfig
import dev.gbenga.inaread.adapters.NetworkResponseInterceptor
import dev.gbenga.inaread.data.auth.LoginResponse
import dev.gbenga.inaread.data.datastore.AccessTokenStore
import dev.gbenga.inaread.data.model.ApiResult
import dev.gbenga.inaread.data.model.MonthlyUsageRequest
import dev.gbenga.inaread.data.model.MonthlyUsageResponse
import dev.gbenga.inaread.data.model.ProfileResponse
import dev.gbenga.inaread.data.network.AuthenticationService
import dev.gbenga.inaread.di.annotations.EncryptedSharedPrefs
import dev.gbenga.inaread.domain.SecureAccessTokenStore
import dev.gbenga.inaread.domain.services.AllUnitUsageApiService
import dev.gbenga.inaread.domain.services.MeterSummaryApiService
import dev.gbenga.inaread.domain.services.ProfileApiService
import dev.gbenga.inaread.ui.metric.MetricsApiService
import dev.gbenga.inaread.utils.FakeMeterSummaryApiService
import dev.gbenga.inaread.utils.FakeMetricsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkHttpClient(accessTokenStore: SecureAccessTokenStore): OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(level = HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(60_000, TimeUnit.SECONDS)
            .readTimeout(60_000, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(NetworkResponseInterceptor(accessTokenStore))
            .build()
    }

    @Provides
    fun provideAccessTokenStore(@EncryptedSharedPrefs prefs: SharedPreferences): SecureAccessTokenStore{
        return AccessTokenStore(prefs);
    }


    // Todo: To be removed
    @Provides
    fun provideMeterSummaryApiService(retrofit: Retrofit): MeterSummaryApiService{
        return retrofit.create(MeterSummaryApiService::class.java)
    }

    @Provides
    fun provideMetricsApiService(retrofit: Retrofit): MetricsApiService{
        return retrofit.create(MetricsApiService::class.java)
    }

    @Provides
    fun provideSettingsRepository(retrofit: Retrofit): ProfileApiService{
        return retrofit.create(ProfileApiService::class.java)
    }

    @Provides
    fun provideAuthenticationService(retrofit: Retrofit): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

    @Provides
    fun provideUnitUsageApiService(): AllUnitUsageApiService {
        return object : AllUnitUsageApiService {
            override suspend fun getAllMonthlyUsage(userId: String): List<MonthlyUsageResponse> {
                return sampleMonthlyUsageList
            }

            override suspend fun uploadMonthlyUsage(
                userId: String,
                monthlyUsageRequest: MonthlyUsageRequest
            ): MonthlyUsageResponse {
                TODO("Not yet implemented")
            }

        }
    }


}


val sampleMonthlyUsageList = listOf(
    MonthlyUsageResponse(
        date = "Tue-12-Nov",
        kilowatt = "320",
        period = "15 days",
        cost = "48.00"
    ),
    MonthlyUsageResponse(
        date = "Tue-13-Nov",
        kilowatt = "295",
        period = "14 days",
        cost = "44.25"
    ),
    MonthlyUsageResponse(
        date = "Mon-12-Dec",
        kilowatt = "310",
        period = "16 days",
        cost = "46.50"
    ),
    MonthlyUsageResponse(
        date = "Wed-01-May",
        kilowatt = "280",
        period = "21 day",
        cost = "42.00"
    ),
    MonthlyUsageResponse(
        date = "Tue-12-Nov",
        kilowatt = "350",
        period = "16 days",
        cost = "52.50"
    ),
    MonthlyUsageResponse(
        date = "Tue-12-Nov",
        kilowatt = "400",
        period = "3 day",
        cost = "60.00"
    ),
    MonthlyUsageResponse(
        date = "Tue-17-Nov",
        kilowatt = "14 days",
        period = "July 2024",
        cost = "67.50"
    ),
    MonthlyUsageResponse(
        date = "Tue-12-Nov",
        kilowatt = "420",
        period = "30 days",
        cost = "63.00"
    ),
    MonthlyUsageResponse(
        date = "Thu-13-Feb",
        kilowatt = "360",
        period = "2 days",
        cost = "54.00"
    ),
    MonthlyUsageResponse(
        date = "Tue-01-Jan",
        kilowatt = "330",
        period = "21 days",
        cost = "49.50"
    )
)
