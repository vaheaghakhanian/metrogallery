package com.ramonapp.metgallery.data.datasource.remote.dto

import com.ramonapp.metgallery.domain.model.ObjectModel


/**
 * Extension functions to map DTOs to model object
 */

fun ObjectDto.toObjectModel(): ObjectModel {
    return ObjectModel(
        primaryImage = primaryImage ?: "",
        additionalImages = additionalImages?.mapNotNull{ it } ?: emptyList(),
        department = department ?: "Unknown",
        objectName = objectName ?: "Unknown",
        title = title ?: "Unknown",
        culture = culture ?: "Unknown",
        period = period ?: "Unknown",
        artistName = artistDisplayName ?: "Unknown",
    )
}