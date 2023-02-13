package com.ramonapp.pixabay.data.remote.datasource.local

import com.google.gson.Gson
import com.ramonapp.pixabay.data.remote.datasource.ImageDataSource
import com.ramonapp.pixabay.domain.model.DataResult
import com.ramonapp.pixabay.domain.model.ImageResponseDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalImageDataSource internal constructor(
    private val cacheDao: CacheDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ImageDataSource {

    private val gson by lazy { Gson() }


    /**
     * Get Images from database and convert from String to object.
     *
     * @param query : The query that stored before.
     * @return : A data result class that shows it is succeeded or failed
     */

    override suspend fun fetchImages(query: String): DataResult<ImageResponseDto> {
        return withContext(ioDispatcher) {
            try {
                gson.fromJson(cacheDao.getByQuery(query)?.response, ImageResponseDto::class.java)
                    ?.let { response ->
                        return@withContext DataResult.Success(response)
                    }
                return@withContext DataResult.Failure(-1, Exception())
            } catch (_: Exception) {
                return@withContext DataResult.Failure(-1, Exception())
            }
        }
    }


    /**
     * Use this to store server data on database.
     *
     * @param query : The query that you want cache.
     * @param result : The result that you want to cache.
     */

    override suspend fun storeImages(query: String, result: ImageResponseDto?) {
        if (result == null) return
        return withContext(ioDispatcher) {
            cacheDao.insertCache(Cache(query, gson.toJson(result)))
        }
    }
}