package kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.repo

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.ApiService
import kg.kyrgyzcoder.altaydillerisozlugu.util.BaseRepository

class UserDataRepository(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun logoutUser(authorization: String) = safeApiCall {
        apiService.logoutUser("application/json", authorization)
    }

}