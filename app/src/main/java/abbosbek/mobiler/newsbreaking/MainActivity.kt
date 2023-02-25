package abbosbek.mobiler.newsbreaking

import abbosbek.mobiler.newsbreaking.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            setContentView(binding.root)
            binding.bottomNav.setupWithNavController(findNavController(R.id.nav_host_fragment))
        }
    }

    fun hideBottomNav(){
        binding.bottomNav.isVisible = false
    }
    fun showBottomNav(){
        binding.bottomNav.isVisible = true
    }

}