package com.ramonapp.metgallery.domain.usecase

import com.ramonapp.metgallery.domain.model.DataResult
import com.ramonapp.metgallery.domain.model.ObjectModel
import com.ramonapp.metgallery.domain.repository.ObjectRepository

class GetObjectUseCase(
    private val repository: ObjectRepository
) {
    suspend operator fun invoke(id: Int): DataResult<ObjectModel> {
        return repository.getObject(id)
    }
}