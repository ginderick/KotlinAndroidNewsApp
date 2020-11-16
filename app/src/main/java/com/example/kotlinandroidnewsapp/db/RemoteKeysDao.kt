package com.example.kotlinandroidnewsapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE articleId = :articleId ORDER BY articleId ASC")
    suspend fun remoteKeysArticlesId(articleId: Int?): RemoteKeys?


    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}