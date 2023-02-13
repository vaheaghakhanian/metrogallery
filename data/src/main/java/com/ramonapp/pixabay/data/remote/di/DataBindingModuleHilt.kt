package com.ramonapp.pixabay.data.remote.di

import com.ramonapp.pixabay.data.remote.datasource.ImageDataSource
import com.ramonapp.pixabay.data.remote.repository.DefaultImageRepository
import com.ramonapp.pixabay.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindingModuleHilt {

    @Binds
    internal abstract fun bindImageRepository(
        defaultImageRepository: DefaultImageRepository,
    ): ImageRepository
}