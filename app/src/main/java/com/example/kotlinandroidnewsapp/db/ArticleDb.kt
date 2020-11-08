package com.example.kotlinandroidnewsapp.db


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlinandroidnewsapp.model.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ArticleDb: RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}