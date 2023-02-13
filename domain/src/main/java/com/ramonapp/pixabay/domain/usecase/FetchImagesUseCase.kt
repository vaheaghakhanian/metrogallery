package com.ramonapp.pixabay.domain.usecase

import com.ramonapp.pixabay.domain.mapper.toImageModel
import com.ramonapp.pixabay.domain.model.DataResult
import com.ramonapp.pixabay.domain.model.ImageModel
import com.ramonapp.pixabay.domain.repository.ImageRepository

class FetchImagesUseCase(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(query: String): DataResult<List<ImageModel>> {
        when(val result = repository.fetchImages(query)) {
            is DataResult.Success -> {
                result.data?.hits?.map { it.toImageModel() }?.let { list ->
                    return DataResult.Success(list)
                } ?: return DataResult.Failure(-1, Exception("Data Model mismatch"))

            }
            is DataResult.Failure -> {
                return DataResult.Failure(result.statusCode, result.e)
            }
        }
    }
}