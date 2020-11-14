package com.example.kotlinandroidnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinandroidnewsapp.R
import com.example.kotlinandroidnewsapp.databinding.FragmentBreakingNewsBinding
import com.example.kotlinandroidnewsapp.databinding.FragmentSavedNewsBinding
import com.example.kotlinandroidnewsapp.ui.NewsViewModel
import com.example.kotlinandroidnewsapp.ui.adapters.ArticlesLoadStateAdapter
import com.example.kotlinandroidnewsapp.ui.adapters.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var newsAdapter: NewsAdapter
    lateinit var binding: FragmentSavedNewsBinding

    private val viewModel: NewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSavedNewsBinding.bind(view)


        setUpRecyclerView()
        directToDetail()
        getSavedNews()


    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            adapter = newsAdapter.withLoadStateHeaderAndFooter(
                header = ArticlesLoadStateAdapter {newsAdapter.retry()},
                footer = ArticlesLoadStateAdapter {newsAdapter.retry()}
            )
            layoutManager = LinearLayoutManager(activity)
            newsAdapter.addLoadStateListener { loadState ->
                // Only show the list if refresh succeeds.
                binding.rvSavedNews.isVisible = loadState.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        requireContext(),
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }

        }
    }


//    private fun observeLiveData() {
//        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
//            newsAdapter.differ.submitList(it)
//        })
//    }

    private fun directToDetail() {
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun getSavedNews() {
        lifecycleScope.launch {
            viewModel.getSavedNews().collectLatest { pagingData ->
                newsAdapter.submitData(pagingData)
            }
        }
    }
}