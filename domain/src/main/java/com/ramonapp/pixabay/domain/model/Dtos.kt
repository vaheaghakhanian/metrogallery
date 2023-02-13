package com.ramonapp.pixabay.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ImageResponseDto(
    @SerializedName("hits") val hits: List<ImageDto>?,
)

data class ImageDto(
    @SerializedName("previewURL") val previewURL: String?,
    @SerializedName("largeImageURL") val largeImageURL: String?,
    @SerializedName("tags") val tags: String?,
    @SerializedName("user") val user: String?,
    @SerializedName("downloads") val downloads: Int?,
    @SerializedName("likes") val likes: Int?,
    @SerializedName("comments") val comments: Int?,
)