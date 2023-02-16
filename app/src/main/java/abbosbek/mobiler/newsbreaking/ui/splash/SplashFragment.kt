package abbosbek.mobiler.newsbreaking.ui.splash

import abbosbek.mobiler.newsbreaking.MainActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import abbosbek.mobiler.newsbreaking.R
import abbosbek.mobiler.newsbreaking.databinding.FragmentDetailsBinding
import abbosbek.mobiler.newsbreaking.databinding.FragmentSplashBinding
import android.content.Context
import android.util.Log
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

class SplashFragment : Fragment() {

    private var _binding : FragmentSplashBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTitle.postDelayed({
            (activity as MainActivity).showBottomBar()
        },3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding == null
    }

}