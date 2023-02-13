package com.ramonapp.pixabay.domain.di

import com.ramonapp.pixabay.domain.repository.ImageRepository
import com.ramonapp.pixabay.domain.usecase.FetchImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModuleHilt {

    @Provides
    fun provideFetchImageUseCase(repository: ImageRepository): FetchImagesUseCase {
        return FetchImagesUseCase(repository)
    }
}