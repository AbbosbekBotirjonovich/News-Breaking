package abbosbek.mobiler.newsbreaking.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import abbosbek.mobiler.newsbreaking.R
import abbosbek.mobiler.newsbreaking.databinding.FragmentDetailsBinding
import abbosbek.mobiler.newsbreaking.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding == null
    }
}