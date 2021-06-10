package kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.repo

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.ApiService
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.model.ModelLoginUser
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelCreateUser
import kg.kyrgyzcoder.altaydillerisozlugu.util.BaseRepository

class AuthRepository(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun loginUser(modelLoginCashier: ModelLoginUser) = safeApiCall {
        apiService.loginUser("application/json", modelLoginCashier)
    }

    suspend fun registerUser(modelCreateUser: ModelCreateUser) = safeApiCall {
        apiService.registerUser("application/json", modelCreateUser)
    }

}