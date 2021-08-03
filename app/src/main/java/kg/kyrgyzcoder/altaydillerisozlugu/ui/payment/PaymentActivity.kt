package kg.kyrgyzcoder.altaydillerisozlugu.ui.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.braintreepayments.cardform.view.CardForm
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivityGetPremiumBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val month = intent.getStringExtra("month")
        val year = intent.getStringExtra("year")

        if (month.isNullOrEmpty()) {
            binding.price.text = year
        }
        else {
            binding.price.text = month
        }

        binding.cardForm.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .setup(this)


    }
}