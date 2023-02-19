package com.ramonapp.metgallery.domain.model

data class ObjectModel(
    val primaryImage: String,
    val additionalImages: List<String>,
    val department: String,
    val objectName: String,
    val title: String,
    val culture: String,
    val period: String,
    val artistName: String,
)