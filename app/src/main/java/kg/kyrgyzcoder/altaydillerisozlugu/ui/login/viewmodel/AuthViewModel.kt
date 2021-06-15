package kg.kyrgyzcoder.altaydillerisozlugu.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.NetworkResponse
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.model.ModelLoginRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.model.ModelLoginUser
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.repo.AuthRepository
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelCreateUser
import kg.kyrgyzcoder.altaydillerisozlugu.ui.login.AuthListener
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private var listener: AuthListener? = null

    fun setListener(listener: AuthListener) {
        this.listener = listener
    }

    fun registerUser(modelCreateUser: ModelCreateUser)  = viewModelScope.launch {
        when (val response = authRepository.registerUser(modelCreateUser)) {
            is NetworkResponse.Success -> {
                listener?.userDataSaved()
            }
            is NetworkResponse.Failure -> {
                listener?.loginFailed(response.errorCode)
            }
        }
    }

    fun loginUser(modelLoginCashier: ModelLoginUser) = viewModelScope.launch {
            when (val response = authRepository.loginUser(modelLoginCashier)) {
                is NetworkResponse.Success -> {
                    saveUserData(response.value)
                }
                is NetworkResponse.Failure -> {
                    listener?.loginFailed(response.errorCode)
                }
            }
    }

    private fun saveUserData(value: ModelLoginRes) = viewModelScope.launch {
        userPreferences.saveUserData(
            value.token,
            value.user_id,
            value.username,
            value.email
        )
        listener?.userDataSaved()
    }


}