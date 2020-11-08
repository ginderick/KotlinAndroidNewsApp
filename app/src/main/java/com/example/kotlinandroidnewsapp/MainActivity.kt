package com.example.kotlinandroidnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinandroidnewsapp.api.NewsAPI
import com.example.kotlinandroidnewsapp.db.ArticleDao
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var articleDao: ArticleDao

    @Inject
    lateinit var newsAPI: NewsAPI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}