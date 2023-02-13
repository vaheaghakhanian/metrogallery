package com.ramonapp.pixabay.domain.repository

import com.ramonapp.pixabay.domain.model.DataResult
import com.ramonapp.pixabay.domain.model.ImageResponseDto

interface ImageRepository {

    /**
     * Fetch Images from Data source by search query
     */
    suspend fun fetchImages(query: String): DataResult<ImageResponseDto>
}