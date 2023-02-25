package abbosbek.mobiler.newsbreaking.ui.main

import abbosbek.mobiler.newsbreaking.MainActivity
import abbosbek.mobiler.newsbreaking.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import abbosbek.mobiler.newsbreaking.adapter.NewsAdapter
import abbosbek.mobiler.newsbreaking.databinding.FragmentMainBinding
import abbosbek.mobiler.newsbreaking.utils.Constants.QUERY_PAGE_SIZE
import abbosbek.mobiler.newsbreaking.utils.Resource
import android.util.Log
import android.widget.AbsListView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding?= null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)
            findNavController().navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundle
            )
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner){response->
            when(response){

                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    isLoading = false
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        val totalPages = it.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.numberPage == totalPages
                    }
                }
                is Resource.Error ->{
                    binding.progressBar.isVisible = false
                    isLoading = false
                    response.data?.let {
                        Log.e("CheckData", "onViewCreated: Error ${it}", )
                    }
                }
                is Resource.Loading ->{
                    binding.progressBar.isVisible = true
                    isLoading = true
                }

            }
        }

    }

    private fun initAdapter() = with(binding) {

        newsAdapter = NewsAdapter()

        val itemDecoration = DividerItemDecoration(requireContext(),RecyclerView.VERTICAL)

        recyclerNews.adapter = newsAdapter
        recyclerNews.addItemDecoration(itemDecoration)
        binding.recyclerNews.addOnScrollListener(this@MainFragment.scrollListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding == null
    }


    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition  = layoutManager.findFirstVisibleItemPosition()

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNoteLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate = isNotLoadingAndNoteLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate){
                viewModel.getNews("us")
                isScrolling = false
            }else{
                binding.recyclerNews.setPadding(0,0,0,0)
            }
        }
    }

}