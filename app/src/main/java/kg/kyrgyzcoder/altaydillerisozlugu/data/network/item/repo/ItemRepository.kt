package kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.repo

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.ApiService
import kg.kyrgyzcoder.altaydillerisozlugu.util.BaseRepository

class ItemRepository(
    private val apiService: ApiService
) : BaseRepository() {

    suspend fun getCategoryList(code: String?, authorization : String, search: String) = safeApiCall {
        apiService.getCategoryList(code,"application/json", authorization, search)
    }

    suspend fun getCategoryListDefUser(code: String?, search: String) = safeApiCall {
        apiService.getCategoryListDefUser(code,"application/json", search)
    }

    suspend fun getWordsList(code: String?,authorization : String, categoryId: Int, search: String) = safeApiCall {
        apiService.getWordsList(code,"application/json", authorization, categoryId, search)
    }

    suspend fun getWordsListDefUser(code: String?,categoryId: Int, search: String) = safeApiCall {
        apiService.getWordsListDefUser(code,"application/json", categoryId, search)
    }

    suspend fun getDescriptionsList(code: String?,authorization : String, categoryId: Int, search: String) = safeApiCall {
        apiService.getDescriptionsList(code,"application/json", authorization, categoryId, search)
    }

    suspend fun getDescriptionsListDefUser(code: String?,categoryId: Int, search: String) = safeApiCall {
        apiService.getDescriptionsListDefUser(code,"application/json", categoryId, search)
    }

}