package kg.kyrgyzcoder.altaydillerisozlugu.ui.main

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivityMainBinding
import kg.kyrgyzcoder.altaydillerisozlugu.util.OnBackPressed


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //this.supportActionBar?.title = ""

        val navView: BottomNavigationView = binding.navBottomView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        window.statusBarColor = ContextCompat.getColor(this, R.color.main_bg)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_main, R.id.navigation_choosen, R.id.navigation_settings, R.id.navigation_profile
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0){
            super.onBackPressed()
        }
        else {
            supportFragmentManager.popBackStack()
        }
    }
}