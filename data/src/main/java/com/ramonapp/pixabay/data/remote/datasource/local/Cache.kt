package com.ramonapp.pixabay.data.remote.datasource.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cache(
    @PrimaryKey @ColumnInfo(name = "query") val query: String,
    @ColumnInfo(name = "response") val response: String
)
