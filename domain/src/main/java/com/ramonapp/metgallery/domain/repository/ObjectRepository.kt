package com.ramonapp.metgallery.domain.repository

import com.ramonapp.metgallery.domain.model.DataResult
import com.ramonapp.metgallery.domain.model.ObjectModel

interface ObjectRepository {

    /**
     * Fetch Objects from Data source by search query.
     */
    suspend fun searchIDs(query: String): DataResult<List<Int>>


    /**
     * Get an Object from Data source by its ID.
     */
    suspend fun getObject(id: Int): DataResult<ObjectModel>
}