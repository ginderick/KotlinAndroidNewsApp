package com.example.kotlinandroidnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kotlinandroidnewsapp.R
import com.example.kotlinandroidnewsapp.api.NewsAPI
import com.example.kotlinandroidnewsapp.db.ArticleDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

    }


}