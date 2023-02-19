package com.ramonapp.metgallery.domain.usecase

import com.ramonapp.metgallery.domain.model.DataResult
import com.ramonapp.metgallery.domain.repository.ObjectRepository

class SearchIDsUseCase(
    private val repository: ObjectRepository
) {
    suspend operator fun invoke(query: String): DataResult<List<Int>> {
        return repository.searchIDs(query)
    }
}