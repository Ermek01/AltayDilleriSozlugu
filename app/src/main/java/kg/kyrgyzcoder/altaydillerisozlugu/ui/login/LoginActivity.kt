package kg.kyrgyzcoder.altaydillerisozlugu.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.model.ModelLoginUser
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivityLoginBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.login.viewmodel.AuthViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.login.viewmodel.AuthViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.MainActivity
import kg.kyrgyzcoder.altaydillerisozlugu.ui.register.RegisterActivity
import kg.kyrgyzcoder.altaydillerisozlugu.util.hide
import kg.kyrgyzcoder.altaydillerisozlugu.util.hideKeyboard
import kg.kyrgyzcoder.altaydillerisozlugu.util.show
import kg.kyrgyzcoder.altaydillerisozlugu.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware, AuthListener {

    override val kodein: Kodein by closestKodein()
    private val authViewModelFactory: AuthViewModelFactory by instance()

    private lateinit var authViewModel: AuthViewModel

    private lateinit var binding: ActivityLoginBinding

    private var mIsShowPass = false

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.main_bg)

        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)
        authViewModel.setListener(this)

        binding.btnLogin.setOnClickListener {

            if (checkInputs()){
                hideKeyboard()
                binding.progressBar.show()
                authViewModel.loginUser(
                    ModelLoginUser(
                        binding.editUsername.text.toString(),
                        binding.editPassword.text.toString()
                    )
                )
            }

        }

        binding.editPassword.setOnTouchListener(View.OnTouchListener { v, event ->

            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.editPassword.right - binding.editPassword.compoundDrawables.get(
                        DRAWABLE_RIGHT
                    ).bounds.width()
                ) {
                    mIsShowPass = !mIsShowPass
                    showPassword(mIsShowPass)
                    return@OnTouchListener true
                }
            }
            false

        })

        showPassword(mIsShowPass)

        binding.txtCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    private fun showPassword(isShow: Boolean){
        if (isShow){
            binding.editPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            val drawable = ContextCompat.getDrawable(this, R.drawable.ic_invisible_psw)
            binding.editPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable,null)
        }
        else{
            binding.editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.editPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visible_psw,0)
        }
        binding.editPassword.setSelection(binding.editPassword.text.toString().length)
    }

    private fun checkInputs(): Boolean {

        var ret = true

        if (binding.editUsername.text.toString().isEmpty()) {
            ret = false
            binding.usernameError.visibility = View.VISIBLE
            binding.usernameError.text = getString(R.string.txt_write_username)
        }
        else{
            binding.usernameError.visibility = View.GONE
        }

        if (binding.editPassword.text.toString().isEmpty()) {
            ret = false
            binding.passwordError.visibility = View.VISIBLE
            binding.passwordError.text = getString(R.string.txt_write_password)
        }
        else{
            binding.passwordError.visibility = View.GONE
        }
        return ret

    }

    override fun loginFailed(code: Int?) {
        binding.passwordError.visibility = View.VISIBLE
        binding.passwordError.text = getString(R.string.pwdUserNameWrong)
        binding.progressBar.hide()
    }

    override fun userDataSaved() {
        binding.progressBar.hide()
        Intent(this, MainActivity::class.java).let { intent ->
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}