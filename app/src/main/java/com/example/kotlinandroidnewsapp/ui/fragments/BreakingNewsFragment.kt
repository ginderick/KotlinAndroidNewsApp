package com.example.kotlinandroidnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinandroidnewsapp.R
import com.example.kotlinandroidnewsapp.ui.NewsViewModel
import com.example.kotlinandroidnewsapp.ui.adapters.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_breaking_news.*

@AndroidEntryPoint
class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    lateinit var newsAdapter: NewsAdapter

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        viewModel.getBreakingNews()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.apply {
            newsLiveData.observe(viewLifecycleOwner, Observer {
                newsAdapter.differ.submitList(it)
                rvBreakingNews.visibility = View.VISIBLE
            })

//            loadingLiveData.observe(viewLifecycleOwner, Observer { isLoading ->
//                pbBreakingNews.visibility = if(isLoading) View.VISIBLE else View.GONE
//                if (isLoading) {
//                    rvBreakingNews.visibility = View.GONE
//                }
//            })
        }
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}