package com.ramonapp.metgallery.data.api

import com.ramonapp.metgallery.data.datasource.remote.dto.ObjectDto
import com.ramonapp.metgallery.data.datasource.remote.dto.SearchIDsDto
import com.ramonapp.metgallery.domain.model.DataResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * API definition to use with Retrofit.
 */
interface MetGalleryApi {


    /**
     * Retrieves the list of images from the server.
     */
    @GET("search")
    suspend fun searchIDs(
        @Query("q") query: String,
    ): DataResult<SearchIDsDto>


    /**
     * Retrieves an Item by its ID.
     */
    @GET("objects/{id}")
    suspend fun getObject(
        @Path("id") id: Int
    ): DataResult<ObjectDto>
}