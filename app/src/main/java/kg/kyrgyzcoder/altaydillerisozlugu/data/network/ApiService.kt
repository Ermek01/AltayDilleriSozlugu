package kg.kyrgyzcoder.altaydillerisozlugu.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelCategoryRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelWordsPag
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.model.ModelLoginRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.model.ModelLoginUser
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelCreateUser
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

interface ApiService {

    @POST("accounts/register/")
    suspend fun registerUser(
        @Header("Content-Type")  content_type:String,
        @Body modelCreateCashier: ModelCreateUser
    )

    @POST("accounts/login/")
    suspend fun loginUser(
        @Header("Content-Type")  content_type:String,
        @Body modelCreateCashier: ModelLoginUser
    ) : ModelLoginRes

    @POST("accounts/logout/")
    suspend fun logoutUser(
        @Header("Content-Type")  content_type:String,
        @Header ("Authorization") authorization:String)

    @GET("category/")
    suspend fun getCategoryList(
        @Header("Content-Type")  content_type:String,
        @Header ("Authorization") value: String,
        @Query("search") q : String
    ): ModelCategoryRes

    @GET("category/")
    suspend fun getCategoryListDefUser(
        @Header("Content-Type")  content_type:String,
        @Query("search") q : String
    ): ModelCategoryRes

    @GET("words/")
    suspend fun getWordsList(
        @Header("Content-Type")  content_type:String,
        @Header ("Authorization") value: String,
        @Query ("category") categoryId: Int,
        @Query("search") q : String
    ): ModelWordsPag

    @GET("words/")
    suspend fun getWordsListDefUser(
        @Header("Content-Type")  content_type:String,
        @Query ("category") categoryId: Int,
        @Query("search") q : String
    ): ModelWordsPag


    companion object {

        private const val BASE_URL = "http://138.68.80.130/api/v1/"

        operator fun invoke(): ApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

    }

}