package com.ramonapp.pixabay.domain.mapper

import com.ramonapp.pixabay.domain.model.ImageDto
import com.ramonapp.pixabay.domain.model.ImageModel

/**
 * Extension functions to map DTOs to model object
 */

fun ImageDto.toImageModel(): ImageModel {
    return ImageModel(
        thumbnailUrl = previewURL ?: "",
        largeImageUrl = largeImageURL ?: "",
        tags = tags ?: "",
        user = user ?: "",
        downloadCount = downloads ?: 0,
        likeCount = likes ?: 0,
        commentCount = comments ?: 0,
    )
}