package abbosbek.mobiler.newsbreaking

import abbosbek.mobiler.newsbreaking.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_splash)

        Handler(Looper.myLooper()!!).postDelayed({
            setContentView(binding.root)
            binding.bottomNav.setupWithNavController(findNavController(R.id.nav_host_fragment))
        },3000)

    }

}