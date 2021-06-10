package kg.kyrgyzcoder.altaydillerisozlugu.ui.splash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.MainActivity
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivitySplashScreenBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.login.LoginActivity
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.LogoutFragment
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils.LanguageListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils.SelectLanguageFragment
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.viewmodel.UserPreferencesViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.viewmodel.UserPreferencesViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.util.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class SplashScreenActivity : AppCompatActivity(), KodeinAware, LanguageListener {
    override val kodein: Kodein by closestKodein()
    private val userPreferencesViewModelFactory: UserPreferencesViewModelFactory by instance()

    private lateinit var userPreferencesViewModel: UserPreferencesViewModel

    val userPreferences : UserPreferences? = null

    private lateinit var binding: ActivitySplashScreenBinding

    private lateinit var bottomAnim: Animation

    private lateinit var circleAnim1 : Animation
    private lateinit var circleAnim2 : Animation
    private lateinit var circleAnim3 : Animation

    private var mIsRecreate: Boolean = false

    private var mCode = ""
    private var nameCountry = ""
    private var mFlag = 0

    override fun onDestroy() {
        super.onDestroy()
        mIsRecreate = true
    }

    override fun getLanguage(flag: Int, name: String, code: String, isRecreate: Boolean) {
        mFlag = flag
        nameCountry = name
        mCode = code
        mIsRecreate = isRecreate
    }

    override fun recreate() {
        super.recreate()

        if (mIsRecreate){
            Log.d("ololo", "recreate")

            if (mCode.isNotEmpty()) {
                binding.imgFlag.setBackgroundResource(mFlag)
                binding.nameCountry.text = nameCountry
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("ololo", "onCreate")

        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)
        circleAnim1 = AnimationUtils.loadAnimation(this, R.anim.circle_anim_240dp)
        circleAnim2 = AnimationUtils.loadAnimation(this, R.anim.circle_anim_350dp)
        circleAnim3 = AnimationUtils.loadAnimation(this, R.anim.circle_anim_450dp)

        userPreferencesViewModel =
            ViewModelProvider(
                this,
                userPreferencesViewModelFactory
            ).get(UserPreferencesViewModel::class.java)





        binding.relativeLayout.setOnClickListener {
            val fm = supportFragmentManager
            val logoutFragment = SelectLanguageFragment(this)
            logoutFragment.show(fm, "")
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        GlobalScope.launch(Dispatchers.Main){
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null){
                delayFor(1)
            }
            else {
                signIn()
            }
        }

    }

    private fun signIn() {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener {
            if (it.isSuccessful) {
                GlobalScope.launch(Dispatchers.Main) {
                    delayFor(2)
                }
            } else {
                toast("ERROR SIGN IN")
            }
        }
    }

    private suspend fun delayFor(code : Int){

        if (!mIsRecreate){

            binding.circle240dp.visibility = View.VISIBLE
            binding.circle240dp.animation = circleAnim1
            delay(1000)
            binding.circle350dp.visibility = View.VISIBLE
            binding.circle350dp.animation = circleAnim2
            delay(1000)
            binding.circle450dp.visibility = View.VISIBLE
            binding.circle450dp.animation = circleAnim3
            delay(500)

            if (code == 1){
                delay(2000)
            }
            userPreferencesViewModel.isUserSignedIn.asLiveData().observe(this, {isUserIn ->

                if (isUserIn.isNullOrEmpty()){

                    binding.circle240dp.visibility = View.GONE
                    binding.circle350dp.visibility = View.GONE
                    binding.circle450dp.visibility = View.GONE

                    binding.relativeLayout.visibility = View.VISIBLE
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.txtWithoutAccount.visibility = View.VISIBLE

                    binding.relativeLayout.animation = bottomAnim
                    binding.btnLogin.animation = bottomAnim
                    binding.txtWithoutAccount.animation = bottomAnim

                    binding.btnLogin.setOnClickListener {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }

                    binding.txtWithoutAccount.setOnClickListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }
                else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            })
        }


    }


}