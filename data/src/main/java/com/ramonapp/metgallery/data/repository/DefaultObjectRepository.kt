package com.ramonapp.metgallery.data.repository

import com.ramonapp.metgallery.data.datasource.ObjectDataSource
import com.ramonapp.metgallery.data.datasource.remote.dto.toObjectModel
import com.ramonapp.metgallery.domain.model.DataResult
import com.ramonapp.metgallery.domain.model.ErrorType
import com.ramonapp.metgallery.domain.model.ObjectModel
import com.ramonapp.metgallery.domain.repository.ObjectRepository
import javax.inject.Inject

class DefaultObjectRepository @Inject constructor(
    private val remoteDataSource: ObjectDataSource,
) : ObjectRepository {

    override suspend fun searchIDs(query: String): DataResult<List<Int>> {
        when (val result = remoteDataSource.searchIDs(query)) {
            is DataResult.Success -> {
                result.data?.objectIDs?.let { ids ->
                    return DataResult.Success(ids)
                } ?: return DataResult.Failure(ErrorType.ParseError)
            }
            is DataResult.Failure -> {
                return result
            }
        }
    }

    override suspend fun getObject(id: Int): DataResult<ObjectModel> {
        when (val result = remoteDataSource.getObject(id)) {
            is DataResult.Success -> {
                result.data?.toObjectModel()?.let { model ->
                    return DataResult.Success(model)
                } ?: return DataResult.Failure(ErrorType.ParseError)
            }
            is DataResult.Failure -> {
                return result
            }
        }
    }
}