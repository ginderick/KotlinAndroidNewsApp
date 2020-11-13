package com.example.kotlinandroidnewsapp.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlinandroidnewsapp.model.Article

@Database(
    entities = [Article::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticleDb: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}