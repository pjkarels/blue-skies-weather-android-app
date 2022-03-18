package com.playground.blueskiesweather.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.playground.blueskiesweather.api.WeatherApi
import com.playground.blueskiesweather.api.WeatherApiClient
import com.playground.blueskiesweather.repository.ForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideApiContract(): WeatherApi {
        val BASE_URL = "http://api.myservice.com/"

        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherApiClient(api: WeatherApi): WeatherApiClient {
        return WeatherApiClient(api)
    }

    @Singleton
    @Provides
    fun provideForecastRepository(weatherApiClient: WeatherApiClient): ForecastRepository {
        return ForecastRepository(weatherApiClient)
    }
}