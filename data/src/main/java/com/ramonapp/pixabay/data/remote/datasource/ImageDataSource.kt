package com.ramonapp.pixabay.data.remote.datasource

import com.ramonapp.pixabay.domain.model.DataResult
import com.ramonapp.pixabay.domain.model.ImageResponseDto

interface ImageDataSource {

    /**
     * Fetch Images by query.
     *
     * @param query : A string to search on server images.
     * @return : A data result class that shows it is succeeded or failed
     */

    suspend fun fetchImages(query: String): DataResult<ImageResponseDto>




    /**
     * Store Images result on data source.
     *
     * @param query : The string that result is related to.
     * @param result : Json result that have to store.
     */

    suspend fun storeImages(query: String, result: ImageResponseDto?)
}