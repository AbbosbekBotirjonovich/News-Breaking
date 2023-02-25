package abbosbek.mobiler.newsbreaking.ui.search

import abbosbek.mobiler.newsbreaking.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import abbosbek.mobiler.newsbreaking.adapter.NewsAdapter
import abbosbek.mobiler.newsbreaking.databinding.FragmentSearchBinding
import abbosbek.mobiler.newsbreaking.utils.Resource
import android.text.Editable
import android.util.Log
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding?= null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var newsAdapter : NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)
            findNavController().navigate(
                R.id.action_searchFragment_to_detailsFragment,
                bundle
            )
        }

        var job : Job ?= null
        binding.etSearch.addTextChangedListener{text : Editable? ->
            job?.cancel()

            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.getSearchNews(query = it.toString())
                    }
                }
            }

        }


        viewModel.searchNewsLiveData.observe(viewLifecycleOwner){response->
            when(response){

                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error ->{
                    binding.progressBar.isVisible = false
                    response.data?.let {
                        Log.e("CheckData", "onViewCreated: Error ${it}", )
                    }
                }
                is Resource.Loading ->{
                    binding.progressBar.isVisible = true
                }

            }
        }

    }

    private fun initAdapter() = with(binding) {

        newsAdapter = NewsAdapter()

        val itemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)

        recyclerSearch.adapter = newsAdapter
        recyclerSearch.addItemDecoration(itemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding == null
    }

}