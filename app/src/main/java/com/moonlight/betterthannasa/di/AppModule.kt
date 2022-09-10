package com.moonlight.betterthannasa.di

import android.app.Application
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.moonlight.betterthannasa.data.local.BetterThanNasaDatabase
import com.moonlight.betterthannasa.data.remote.BetterThanNasaApi
import com.moonlight.betterthannasa.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        return httpClient.addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun provideBetterThanNasaApi(retrofit: Retrofit): BetterThanNasaApi =
        retrofit.create(BetterThanNasaApi::class.java)


    @Singleton
    @Provides
    fun provideBetterThanNasaDatabase(app: Application): BetterThanNasaDatabase {
        return Room.databaseBuilder(
            app,
            BetterThanNasaDatabase::class.java,
            "betterThanNasa.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }
}

