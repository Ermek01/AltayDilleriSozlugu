package kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.repo

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.ApiService
import kg.kyrgyzcoder.altaydillerisozlugu.util.BaseRepository

class ItemRepository(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun getCategoryList(authorization : String, search: String) = safeApiCall {
        apiService.getCategoryList("application/json", authorization, search)
    }

    suspend fun getCategoryListDefUser(search: String) = safeApiCall {
        apiService.getCategoryListDefUser("application/json", search)
    }

    suspend fun getWordsList(authorization : String, categoryId: Int, search: String) = safeApiCall {
        apiService.getWordsList("application/json", authorization, categoryId, search)
    }

    suspend fun getWordsListDefUser(categoryId: Int, search: String) = safeApiCall {
        apiService.getWordsListDefUser("application/json", categoryId, search)
    }

}