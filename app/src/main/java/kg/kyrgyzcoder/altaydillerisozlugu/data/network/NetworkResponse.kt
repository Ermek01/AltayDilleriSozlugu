package kg.kyrgyzcoder.altaydillerisozlugu.data.network

import okhttp3.ResponseBody

sealed class NetworkResponse<out T> {

    data class Success<out T>(val value: T) : NetworkResponse<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : NetworkResponse<Nothing>()

}