package com.ramonapp.pixabay.data.remote.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CacheDao {

    @Query("SELECT * FROM cache WHERE query = :query")
    suspend fun getByQuery(query: String): Cache?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(cache: Cache)

}