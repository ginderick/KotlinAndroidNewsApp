package com.example.kotlinandroidnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinandroidnewsapp.R
import com.example.kotlinandroidnewsapp.ui.NewsViewModel
import com.example.kotlinandroidnewsapp.ui.adapters.ArticlesLoadStateAdapter
import com.example.kotlinandroidnewsapp.ui.adapters.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {

    lateinit var newsAdapter: NewsAdapter

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        getBreakingNews()
//        Log.d("Data:", newsAdapter.differ.toString())
//        observeLiveData()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun getBreakingNews() {
        lifecycleScope.launch {
            viewModel.getBreakingNews().collectLatest { pagingData ->
                newsAdapter.submitData(pagingData)
            }
        }

    }

//    private fun observeLiveData() {
//        viewModel.apply {
//            newsLiveData.observe(viewLifecycleOwner, Observer {
//                newsAdapter.differ.submitList(it)
//            })
//        }
//    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            adapter = newsAdapter.withLoadStateHeaderAndFooter(
                header = ArticlesLoadStateAdapter {newsAdapter.retry()},
                footer = ArticlesLoadStateAdapter {newsAdapter.retry()}
            )
            layoutManager = LinearLayoutManager(activity)
        }
    }
}