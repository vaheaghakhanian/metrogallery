package com.ramonapp.pixabay.data.remote.di

import android.content.Context
import androidx.room.Room
import com.ramonapp.pixabay.data.remote.api.ApiClient
import com.ramonapp.pixabay.data.remote.api.PixabayApi
import com.ramonapp.pixabay.data.remote.datasource.ImageDataSource
import com.ramonapp.pixabay.data.remote.datasource.local.LocalImageDataSource
import com.ramonapp.pixabay.data.remote.datasource.local.PixabayDatabase
import com.ramonapp.pixabay.data.remote.datasource.remote.RemoteImageDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModuleHilt {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource

    @Provides
    @Singleton
    internal fun providePixabayApi(): PixabayApi {
        return ApiClient.getRetrofitInstance().create(PixabayApi::class.java)
    }

    @RemoteDataSource
    @Singleton
    @Provides
    internal fun provideRemoteImageDataSource(
        remoteDataSource: RemoteImageDataSource
    ): ImageDataSource = remoteDataSource

    @LocalDataSource
    @Singleton
    @Provides
    fun provideLocalImageDataSource(
        database: PixabayDatabase, ioDispatcher: CoroutineDispatcher
    ): ImageDataSource {
        return LocalImageDataSource(
            database.cacheDao(), ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): PixabayDatabase {
        return Room.databaseBuilder(
            context.applicationContext, PixabayDatabase::class.java, "database.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}