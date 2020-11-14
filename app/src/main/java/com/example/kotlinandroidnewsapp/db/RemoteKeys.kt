package com.example.kotlinandroidnewsapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = true)
    val articleId: Int?,
    val prevKey: Int?,
    val nextKey: Int?
) {

}