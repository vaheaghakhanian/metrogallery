package com.ramonapp.metgallery.data.di

import com.ramonapp.metgallery.data.datasource.ObjectDataSource
import com.ramonapp.metgallery.data.datasource.remote.RemoteObjectDataSource
import com.ramonapp.metgallery.data.repository.DefaultObjectRepository
import com.ramonapp.metgallery.domain.repository.ObjectRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindingModuleHilt {

    @Singleton
    @Binds
    internal abstract fun bindObjectRepository(
        defaultObjectRepository: DefaultObjectRepository,
    ): ObjectRepository

    @Singleton
    @Binds
    internal abstract fun bindObjectDataSource(
        remoteDataSource: RemoteObjectDataSource
    ): ObjectDataSource

}