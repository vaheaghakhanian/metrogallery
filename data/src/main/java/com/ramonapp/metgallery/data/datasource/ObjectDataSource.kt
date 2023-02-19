package com.ramonapp.metgallery.data.datasource

import com.ramonapp.metgallery.data.datasource.remote.dto.ObjectDto
import com.ramonapp.metgallery.data.datasource.remote.dto.SearchIDsDto
import com.ramonapp.metgallery.domain.model.DataResult

interface ObjectDataSource {

    /**
     * Search IDs by query.
     *
     * @param query : A string to search on server objects.
     * @return : A data result class that shows it is succeeded or failed
     */

    suspend fun searchIDs(query: String): DataResult<SearchIDsDto>




    /**
     * Get an Object detail by its ID.
     *
     * @param id : ID of the Object.
     * @return : Return the detail of an Object.
     */

    suspend fun getObject(id: Int): DataResult<ObjectDto>
}