package kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.repo

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.ApiService
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavorites
import kg.kyrgyzcoder.altaydillerisozlugu.util.BaseRepository

class FavoriteRepository(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun addFavorites(code: String?, authorization : String, modelFavorites: ModelFavorites) = safeApiCall {
        apiService.addFavorites(code, authorization,"application/json",  modelFavorites)
    }

}