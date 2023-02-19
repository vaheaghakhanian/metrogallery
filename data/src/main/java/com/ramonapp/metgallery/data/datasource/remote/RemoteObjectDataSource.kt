package com.ramonapp.metgallery.data.datasource.remote

import com.ramonapp.metgallery.data.api.MetGalleryApi
import com.ramonapp.metgallery.data.datasource.ObjectDataSource
import com.ramonapp.metgallery.data.datasource.remote.dto.ObjectDto
import com.ramonapp.metgallery.data.datasource.remote.dto.SearchIDsDto
import com.ramonapp.metgallery.domain.model.DataResult
import javax.inject.Inject

class RemoteObjectDataSource @Inject constructor(
    private val metGalleryApi: MetGalleryApi
) : ObjectDataSource {

    override suspend fun searchIDs(query: String): DataResult<SearchIDsDto> {
        return metGalleryApi.searchIDs(query)
    }

    override suspend fun getObject(id: Int): DataResult<ObjectDto> {
        return metGalleryApi.getObject(id)
    }


}