package com.example.kotlinandroidnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kotlinandroidnewsapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    private val navController by lazy { findNavController(R.id.newsNavHostFragment) }
    private val appBarConfiguration by lazy { AppBarConfiguration(topLevelDestinationIds = setOf(R.id.breakingNewsFragment, R.id.searchNewsFragment, R.id.savedNewsFragment)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        setupBottomNavigationBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController
            .navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupActionBar() {
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupBottomNavigationBar() {
        bottomNavigationView.setupWithNavController(navController)
    }
}