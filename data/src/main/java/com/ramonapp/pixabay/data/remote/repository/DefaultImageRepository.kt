package com.ramonapp.pixabay.data.remote.repository

import com.ramonapp.pixabay.data.remote.datasource.ImageDataSource
import com.ramonapp.pixabay.data.remote.di.DataModuleHilt
import com.ramonapp.pixabay.domain.model.DataResult
import com.ramonapp.pixabay.domain.model.ImageResponseDto
import com.ramonapp.pixabay.domain.repository.ImageRepository
import javax.inject.Inject

class DefaultImageRepository @Inject constructor (
    @DataModuleHilt.RemoteDataSource private val remoteDataSource: ImageDataSource,
    @DataModuleHilt.LocalDataSource private val localDataSource: ImageDataSource
) : ImageRepository {
    override suspend fun fetchImages(query: String): DataResult<ImageResponseDto> {
        when (val result = remoteDataSource.fetchImages(query)) {
            is DataResult.Success -> {
                localDataSource.storeImages(query, result.data)
                return result
            }
            is DataResult.Failure -> {
                localDataSource.fetchImages(query).let { dbResult ->
                    return if (dbResult is DataResult.Success) dbResult else result
                }
            }
        }
    }
}