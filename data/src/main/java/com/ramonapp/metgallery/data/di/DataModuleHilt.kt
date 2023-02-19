package com.ramonapp.metgallery.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ramonapp.metgallery.data.BuildConfig
import com.ramonapp.metgallery.data.api.DefaultCallAdapterFactory
import com.ramonapp.metgallery.data.api.MetGalleryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModuleHilt {

    private const val CONNECTION_TIME_OUT = 5L
    private const val READ_TIME_OUT = 15L
    private const val WRITE_TIME_OUT = 15L


    @Provides
    @Singleton
    internal fun provideMetGalleryApi(retrofit: Retrofit): MetGalleryApi {
        return retrofit.create(MetGalleryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson, client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(DefaultCallAdapterFactory())
            .client(client)
            .build()

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder()
        .setLenient()
        .serializeNulls()
        .create()

    @Singleton
    @Provides
    fun provideOkhttpClient() = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}