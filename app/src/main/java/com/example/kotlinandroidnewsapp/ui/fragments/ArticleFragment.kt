package com.example.kotlinandroidnewsapp.ui.fragments

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.kotlinandroidnewsapp.R
import com.example.kotlinandroidnewsapp.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article.*


@AndroidEntryPoint
class ArticleFragment: Fragment(R.layout.fragment_article) {


    private val viewModel: NewsViewModel by viewModels()
    val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            webView.settings.setAppCachePath(requireContext().cacheDir.absolutePath)
            webView.settings.allowFileAccess = true
            webView.settings.setAppCacheEnabled(true)
            webView.settings.javaScriptEnabled = true
            webView.settings.cacheMode = WebSettings.LOAD_DEFAULT

            if (!isInternetAvailable(context)) { // loading offline
                Snackbar.make(this, "Viewing offline", Snackbar.LENGTH_LONG).show()
                webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK;
            }
            article.url.let {
                loadUrl(it)
            }
        }


//        fab.setOnClickListener {
//            viewModel.saveNews(article)
//            Snackbar.make(view, "Saved successfully", Snackbar.LENGTH_SHORT).show()
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle the up button here
        return NavigationUI.onNavDestinationSelected(
            item!!,
            requireView()!!.findNavController()
        )
                || super.onOptionsItemSelected(item)
    }

    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        Log.d("Connected:", result.toString())

        return result
    }
}