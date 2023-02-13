package com.ramonapp.pixabay.data.remote.datasource.remote

import com.ramonapp.pixabay.data.remote.api.PixabayApi
import com.ramonapp.pixabay.data.remote.datasource.ImageDataSource
import com.ramonapp.pixabay.domain.model.DataResult
import com.ramonapp.pixabay.domain.model.ImageResponseDto
import javax.inject.Inject

class RemoteImageDataSource @Inject constructor(
    private val pixabayApi: PixabayApi
) : ImageDataSource {

    override suspend fun fetchImages(query: String): DataResult<ImageResponseDto> {
        return pixabayApi.fetchImages(query)
    }


    /**
     * Empty implementation because there is no way to store on remote.
     */
    override suspend fun storeImages(query: String, result: ImageResponseDto?) {
    }
}