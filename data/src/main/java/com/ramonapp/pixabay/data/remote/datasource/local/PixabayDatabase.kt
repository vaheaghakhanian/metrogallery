package com.ramonapp.pixabay.data.remote.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cache::class], version = 1, exportSchema = false)
abstract class PixabayDatabase : RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}