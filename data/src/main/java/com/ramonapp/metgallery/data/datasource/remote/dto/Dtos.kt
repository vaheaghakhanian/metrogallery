package com.ramonapp.metgallery.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class SearchIDsDto(
    @SerializedName("total") val total: Int?,
    @SerializedName("objectIDs") val objectIDs: List<Int>?,
)

data class ObjectDto(
    @SerializedName("primaryImage") val primaryImage: String?,
    @SerializedName("additionalImages") val additionalImages: List<String?>?,
    @SerializedName("department") val department: String?,
    @SerializedName("objectName") val objectName: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("culture") val culture: String?,
    @SerializedName("period") val period: String?,
    @SerializedName("artistDisplayName") val artistDisplayName: String?,
)