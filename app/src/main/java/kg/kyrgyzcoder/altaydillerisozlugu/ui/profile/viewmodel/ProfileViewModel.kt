package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.NetworkResponse
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.repo.UserDataRepository
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.ProfileListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userPreferences: UserPreferences,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private var listener: ProfileListener? = null

    fun setProfileListener(listener: ProfileListener) {
        this.listener = listener
    }

    fun logoutUser() = viewModelScope.launch {
            userPreferences.currentUserToken.collectLatest { token ->
                when (val response = userDataRepository.logoutUser("Token $token")) {
                    is NetworkResponse.Success -> {
                        listener?.logoutSuccess()
                        userPreferences.logoutUser()
                    }
                    is NetworkResponse.Failure -> {
                        listener?.logoutFail(response.errorCode)
                    }
                }
        }

    }


}