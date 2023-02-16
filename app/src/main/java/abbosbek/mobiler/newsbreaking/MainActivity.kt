package abbosbek.mobiler.newsbreaking

import abbosbek.mobiler.newsbreaking.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideBottomBar()
        binding.bottomNav.setupWithNavController(findNavController(R.id.nav_host_fragment))
    }

    fun hideBottomBar() {
        binding.bottomNav.visibility = View.GONE
        val controller = findNavController(R.id.nav_host_fragment)
        controller.navigate(R.id.action_mainFragment_to_splashFragment)
    }

    fun showBottomBar(){
        binding.bottomNav.visibility = View.VISIBLE
        val controller = findNavController(R.id.nav_host_fragment)
        controller.navigate(R.id.action_splashFragment_to_mainFragment)
    }

}