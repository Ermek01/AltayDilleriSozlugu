package kg.kyrgyzcoder.altaydillerisozlugu.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseAuth
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivitySplashScreenBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.login.LoginActivity
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.MainActivity
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils.LanguageListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils.SelectLanguageFragment
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.viewmodel.UserPreferencesViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.viewmodel.UserPreferencesViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.lang.Exception
import java.util.*


class SplashScreenActivity : AppCompatActivity(), KodeinAware, LanguageListener {
    override val kodein: Kodein by closestKodein()
    private val userPreferencesViewModelFactory: UserPreferencesViewModelFactory by instance()

    private lateinit var userPreferencesViewModel: UserPreferencesViewModel

    val userPreferences: UserPreferences? = null

    private lateinit var binding: ActivitySplashScreenBinding

    private lateinit var bottomAnim: Animation

    private lateinit var circleAnim1: Animation
    private lateinit var circleAnim2: Animation
    private lateinit var circleAnim3: Animation
    private lateinit var circleAnim4: Animation

    private var mIsRecreate: Boolean = false

    private var mCode = ""
    private var code: String? = ""
    private var defcode: String? = "ky"
    private var defNameCountry: String? = "Крыгызча"
    private var defFlag: Int = 2131231164
    private var name: String? = ""
    private var flag: Int = 0
    private var nameCountry = ""
    private var mFlag = 0


    override fun getLanguage(flag: Int, name: String, code: String) {
        mFlag = flag
        nameCountry = name
        mCode = code
        val pref = applicationContext.getSharedPreferences("language", Context.MODE_PRIVATE)
        val editor = pref.edit()

        editor.putInt(FLAG_KEY, mFlag)
        editor.putString(CODE_KEY, mCode)
        editor.putString(NAME_KEY, nameCountry)
        editor.apply()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLanguage()
        try {
            binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        }
        catch (e : Exception) {
            Log.d("ololo", e.toString())
        }
        setContentView(binding.root)

        if (flag != 0) {
            binding.imgFlag.setImageResource(flag)
            binding.nameCountry.text = name

        }

        Log.d("ololo", "onCreate")

        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)
        circleAnim1 = AnimationUtils.loadAnimation(this, R.anim.circle_anim_240dp)
        circleAnim2 = AnimationUtils.loadAnimation(this, R.anim.circle_anim_350dp)
        circleAnim3 = AnimationUtils.loadAnimation(this, R.anim.circle_anim_450dp)
        circleAnim4 = AnimationUtils.loadAnimation(this, R.anim.circle_anim_550dp)

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

        GlobalScope.launch(Dispatchers.Main) {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                delayFor(1)
            } else {
                signIn()
            }
        }

    }

    private fun loadLanguage() {
        val pref = getSharedPreferences("language", Context.MODE_PRIVATE)
        flag = pref.getInt(FLAG_KEY, 0)
        code = pref.getString(CODE_KEY, "")
        name = pref.getString(NAME_KEY, "")
        val language = pref.getString(LANGUAGE_KEY, "")


        val locale = Locale(code!!)
        Locale.setDefault(locale)
        val config = Configuration()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locale = locale
            baseContext.resources.updateConfiguration(
                config,
                baseContext.resources.displayMetrics
            )
        } else {
            config.setLocale(locale)
        }

        baseContext.applicationContext.createConfigurationContext(config)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("lang", code)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val code = savedInstanceState.getString("lang")
        val locale = Locale(code)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        applicationContext.createConfigurationContext(config)
        applicationContext.resources.updateConfiguration(
            config,
            applicationContext.resources.displayMetrics
        )
    }

    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {

        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)

        super.applyOverrideConfiguration(overrideConfiguration)
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

    private suspend fun delayFor(code: Int) {

        if (!mIsRecreate) {

            binding.circle240dp.visibility = View.VISIBLE
            binding.circle240dp.animation = circleAnim1
            delay(800)
            binding.circle350dp.visibility = View.VISIBLE
            binding.circle350dp.animation = circleAnim2
            delay(800)
            binding.circle450dp.visibility = View.VISIBLE
            binding.circle450dp.animation = circleAnim3
            delay(800)
            binding.circle550dp.visibility = View.VISIBLE
            binding.circle550dp.animation = circleAnim4
            delay(200)

            if (code == 1) {
                delay(2000)
            }
            userPreferencesViewModel.isUserSignedIn.asLiveData().observe(this, { isUserIn ->

                if (isUserIn.isNullOrEmpty()) {

                    binding.circle240dp.visibility = View.GONE
                    binding.circle350dp.visibility = View.GONE
                    binding.circle450dp.visibility = View.GONE
                    binding.circle550dp.visibility = View.GONE

                    binding.relativeLayout.visibility = View.VISIBLE
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.txtWithoutAccount.visibility = View.VISIBLE

                    binding.relativeLayout.animation = bottomAnim
                    binding.btnLogin.animation = bottomAnim
                    binding.txtWithoutAccount.animation = bottomAnim

                    binding.btnLogin.setOnClickListener {

                        val pref = getSharedPreferences("language",Context.MODE_PRIVATE)
                        val code = pref.getString(CODE_KEY, "")

                        if (code!!.isEmpty()) {
                            val pref = applicationContext.getSharedPreferences("language", Context.MODE_PRIVATE)
                            val editor = pref.edit()
                            editor.putString(CODE_KEY, defcode)
                            editor.putInt(FLAG_KEY, defFlag)
                            editor.putString(NAME_KEY, defNameCountry)
                            editor.apply()
                        }

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }

                    binding.txtWithoutAccount.setOnClickListener {
                        val pref = getSharedPreferences("language",Context.MODE_PRIVATE)
                        val code = pref.getString(CODE_KEY, "")

                        if (code!!.isEmpty()) {
                            val pref = applicationContext.getSharedPreferences("language", Context.MODE_PRIVATE)
                            val editor = pref.edit()
                            editor.putInt(FLAG_KEY, defFlag)
                            editor.putString(CODE_KEY, defcode)
                            editor.putString(NAME_KEY, defNameCountry)
                            editor.apply()
                        }

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }

                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            })
        }


    }


}