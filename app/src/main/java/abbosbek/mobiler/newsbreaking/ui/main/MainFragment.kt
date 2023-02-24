package abbosbek.mobiler.newsbreaking.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import abbosbek.mobiler.newsbreaking.adapter.NewsAdapter
import abbosbek.mobiler.newsbreaking.databinding.FragmentMainBinding
import abbosbek.mobiler.newsbreaking.utils.Resource
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
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

        viewModel.newsLiveData.observe(viewLifecycleOwner){response->
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

        val itemDecoration = DividerItemDecoration(requireContext(),RecyclerView.VERTICAL)

        recyclerNews.adapter = newsAdapter
        recyclerNews.addItemDecoration(itemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding == null
    }
}