package com.ramonapp.pixabay

import com.ramonapp.pixabay.domain.model.DataResult
import com.ramonapp.pixabay.domain.model.ImageDto
import com.ramonapp.pixabay.domain.model.ImageResponseDto
import com.ramonapp.pixabay.domain.repository.ImageRepository

class FakeRepository : ImageRepository {
    override suspend fun fetchImages(query: String): DataResult<ImageResponseDto> {
        val list = mutableListOf<ImageDto>()
        list.add(ImageDto("","","","",0,0,0))
        list.add(ImageDto("","","","",0,0,0))
        list.add(ImageDto("","","","",0,0,0))
        return DataResult.Success(ImageResponseDto(list))
    }
}