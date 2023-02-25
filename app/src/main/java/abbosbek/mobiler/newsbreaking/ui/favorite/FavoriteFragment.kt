package abbosbek.mobiler.newsbreaking.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import abbosbek.mobiler.newsbreaking.R
import abbosbek.mobiler.newsbreaking.adapter.NewsAdapter
import abbosbek.mobiler.newsbreaking.databinding.FragmentDetailsBinding
import abbosbek.mobiler.newsbreaking.databinding.FragmentFavoriteBinding
import abbosbek.mobiler.newsbreaking.ui.detail.DetailViewModel
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding?= null
    private val binding get() = _binding!!

    private lateinit var newsAdapter : NewsAdapter
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)

            findNavController()
                .navigate(R.id.action_favoriteFragment_to_detailsFragment,bundle)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]

                viewModel.deleteArticle(article)
                Snackbar.make(view,"Successfully deleted article",Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.saveFavoriteArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerSaved)
        }

        viewModel.getSavedArticles().observe(viewLifecycleOwner){articles->
            newsAdapter.differ.submitList(articles)
        }

    }

    private fun initAdapter() = with(binding) {

        newsAdapter = NewsAdapter()

        val itemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)

        recyclerSaved.adapter = newsAdapter
        recyclerSaved.addItemDecoration(itemDecoration)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding == null
    }
}