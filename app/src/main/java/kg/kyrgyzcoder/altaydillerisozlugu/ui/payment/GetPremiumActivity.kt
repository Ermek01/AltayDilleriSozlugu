package kg.kyrgyzcoder.altaydillerisozlugu.ui.payment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivityGetPremiumBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivitySplashScreenBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.SplashScreenActivity

class GetPremiumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGetPremiumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetPremiumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        binding.month.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("month", "100c")
            startActivity(intent)
        }

        binding.year.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("year", "1100c")
            startActivity(intent)
        }

    }
}