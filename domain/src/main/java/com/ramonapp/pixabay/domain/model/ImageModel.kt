package com.ramonapp.pixabay.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageModel(
    val thumbnailUrl: String,
    val largeImageUrl: String,
    val tags: String,
    val user: String,
    val downloadCount: Int,
    val likeCount: Int,
    val commentCount: Int,
) : Parcelable
