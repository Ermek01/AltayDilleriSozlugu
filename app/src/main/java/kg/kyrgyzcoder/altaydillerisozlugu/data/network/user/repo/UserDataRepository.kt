package kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.repo

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.ApiService
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelEditProfile
import kg.kyrgyzcoder.altaydillerisozlugu.util.BaseRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserDataRepository(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun logoutUser(authorization: String) = safeApiCall {
        apiService.logoutUser("application/json", authorization)
    }

    suspend fun profileUser(authorization: String) = safeApiCall {
        apiService.getProfileUser("application/json", authorization)
    }

    suspend fun editProfileUser(authorization: String, image: MultipartBody.Part?, username: String,) = safeApiCall {
        apiService.editProfileUser(authorization, image, username)
    }

}