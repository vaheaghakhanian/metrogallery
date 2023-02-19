package com.ramonapp.metgallery.domain.di

import com.ramonapp.metgallery.domain.repository.ObjectRepository
import com.ramonapp.metgallery.domain.usecase.GetObjectUseCase
import com.ramonapp.metgallery.domain.usecase.SearchIDsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModuleHilt {


    @Provides
    fun provideSearchIDsUseCase(repository: ObjectRepository): SearchIDsUseCase {
        return SearchIDsUseCase(repository)
    }

    @Provides
    fun provideGetObjectUseCase(repository: ObjectRepository): GetObjectUseCase {
        return GetObjectUseCase(repository)
    }
}