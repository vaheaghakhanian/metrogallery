package com.ramonapp.pixabay.data.remote.api

import com.ramonapp.pixabay.domain.model.DataResult
import com.ramonapp.pixabay.domain.model.ImageResponseDto
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * API definition to use with Retrofit.
 */
interface PixabayApi {


    /**
     * Retrieves the list of images from the server.
     */
    @GET("/api/")
    suspend fun fetchImages(
        @Query("q") query: String,
    ): DataResult<ImageResponseDto>
}