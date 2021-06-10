package kg.kyrgyzcoder.altaydillerisozlugu.ui.register

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelCreateUser
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivityLoginBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivityRegisterBinding
import kg.kyrgyzcoder.altaydillerisozlugu.ui.login.AuthListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.login.viewmodel.AuthViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.ui.login.viewmodel.AuthViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.util.hide
import kg.kyrgyzcoder.altaydillerisozlugu.util.hideKeyboard
import kg.kyrgyzcoder.altaydillerisozlugu.util.show
import kg.kyrgyzcoder.altaydillerisozlugu.util.toast
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class RegisterActivity : AppCompatActivity(), KodeinAware, AuthListener {

    override val kodein: Kodein by closestKodein()
    private val authViewModelFactory: AuthViewModelFactory by instance()

    private lateinit var authViewModel: AuthViewModel

    private lateinit var binding: ActivityRegisterBinding

    private var mIsShowPass = false

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)
        authViewModel.setListener(this)

        window.statusBarColor = ContextCompat.getColor(this, R.color.main_bg)

        binding.btnRegister.setOnClickListener {
            if (checkFields()) {
                val modelCreateUser = ModelCreateUser(
                    binding.editUsername.text.toString(),
                    binding.editEmail.text.toString(),
                    binding.editPassword.text.toString(),
                    binding.editPasswordConfirm.text.toString()
                )
                binding.progressBar.show()
                authViewModel.registerUser(modelCreateUser)
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

        binding.editPasswordConfirm.setOnTouchListener(View.OnTouchListener { v, event ->

            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.editPasswordConfirm.right - binding.editPasswordConfirm.compoundDrawables.get(
                        DRAWABLE_RIGHT
                    ).bounds.width()
                ) {
                    mIsShowPass = !mIsShowPass
                    showPasswordConfirm(mIsShowPass)
                    return@OnTouchListener true
                }
            }
            false

        })

        showPasswordConfirm(mIsShowPass)


        binding.txtLoginAccount.setOnClickListener {
            finish()
        }

    }

    private fun showPasswordConfirm(mIsShowPass: Boolean) {
        if (mIsShowPass){
            binding.editPasswordConfirm.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.editPasswordConfirm.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_invisible_psw,0)
        }
        else{
            binding.editPasswordConfirm.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.editPasswordConfirm.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visible_psw,0)
        }
        binding.editPasswordConfirm.setSelection(binding.editPasswordConfirm.text.toString().length)
    }

    private fun showPassword(isShow: Boolean){
        if (isShow){
            binding.editPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.editPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_invisible_psw,0)
        }
        else{
            binding.editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.editPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visible_psw,0)
        }
        binding.editPassword.setSelection(binding.editPassword.text.toString().length)
    }

    private fun checkFields(): Boolean {
        var ret = true

        if (binding.editUsername.text.toString().isEmpty()) {
            ret = false
            binding.userNameError.visibility = View.VISIBLE
        }
        else{
            binding.userNameError.visibility = View.GONE
        }

        if (binding.editPassword.text.toString().isEmpty()) {
            ret = false
            binding.passwordError.visibility = View.VISIBLE
        }
        else{
            binding.passwordError.visibility = View.GONE
        }

        if (binding.editPasswordConfirm.text.toString().isEmpty()) {
            ret = false
            binding.confirmPasswordError.visibility = View.VISIBLE
        }
        else{
            binding.confirmPasswordError.visibility = View.GONE
        }

        if (binding.editEmail.text.toString().isEmpty()) {
            ret = false
            binding.emailError.visibility = View.VISIBLE
        }
        else{
            binding.emailError.visibility = View.GONE
        }
        return ret
    }

    override fun loginFailed(code: Int?) {
        binding.progressBar.hide()
        toast("ERROR: $code")
    }

    override fun userDataSaved() {
        binding.progressBar.hide()
        toast(getString(R.string.txt_create_success_account))
        onBackPressed()
    }
}