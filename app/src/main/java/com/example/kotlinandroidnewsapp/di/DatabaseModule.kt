package com.example.kotlinandroidnewsapp.di

import android.content.Context
import androidx.room.Room
import com.example.kotlinandroidnewsapp.db.ArticleDb
import com.example.kotlinandroidnewsapp.db.RemoteKeys
import com.example.kotlinandroidnewsapp.util.Constants.ARTICLE_DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesArticleDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ArticleDb::class.java,
        ARTICLE_DB_NAME
    ).build()


    @Singleton
    @Provides
    fun provideArticleDao(db: ArticleDb) = db.getArticleDao()
}