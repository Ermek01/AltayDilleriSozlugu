package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.NetworkResponse
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelEditProfile
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.repo.UserDataRepository
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.EditProfileListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.LogoutListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util.ProfileListener
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel(
    private val userPreferences: UserPreferences,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    val userToken = userPreferences.currentUserToken

    private var listener: LogoutListener? = null
    private var profileListener: ProfileListener? = null
    private var editProfileListener: EditProfileListener? = null

    fun setLogoutListener(listener: LogoutListener) {
        this.listener = listener
    }

    fun setProfileListener(profileListener: ProfileListener) {
        this.profileListener = profileListener
    }

    fun setEditProfileListener(editProfileListener: EditProfileListener) {
        this.editProfileListener = editProfileListener
    }

    fun logoutUser() = viewModelScope.launch {
        userPreferences.currentUserToken.collect { token ->
            if (token!!.isNotEmpty()){
                when (val response = userDataRepository.logoutUser("Token $token")) {
                    is NetworkResponse.Success -> {
                        userPreferences.logoutUser()
                        listener?.logoutSuccess()

                    }
                    is NetworkResponse.Failure -> {
                        listener?.logoutFail(response.errorCode)
                    }
                }
            }
        }

    }

    fun profileUser() = viewModelScope.launch {
        userPreferences.currentUserToken.collect { token ->
            if (token?.isNotEmpty() == true){
                when (val response = userDataRepository.profileUser("Token $token")) {
                    is NetworkResponse.Success -> {
                        profileListener?.getProfileSuccess(response.value)

                    }
                    is NetworkResponse.Failure -> {
                        profileListener?.getProfileFailure(response.errorCode)
                    }
                }
            }
        }

    }

    fun editProfileUser(image: MultipartBody.Part?, username: RequestBody) = viewModelScope.launch {
        userPreferences.currentUserToken.collect { token ->
            if (token?.isNotEmpty() == true){
                when (val response = userDataRepository.editProfileUser("Token $token", image, username)) {
                    is NetworkResponse.Success -> {
                        editProfileListener?.editProfileSuccess()

                    }
                    is NetworkResponse.Failure -> {
                        editProfileListener?.editProfileFailure(response.errorCode)
                    }
                }
            }
        }

    }
}