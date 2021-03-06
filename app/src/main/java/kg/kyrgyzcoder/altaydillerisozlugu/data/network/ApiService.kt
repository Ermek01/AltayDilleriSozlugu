package kg.kyrgyzcoder.altaydillerisozlugu.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelDescFavorites
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelEditProfile
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavorites
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavoritesRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelCategoryRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelDescriptionsPag
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelWordsPag
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.model.ModelLoginRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.model.ModelLoginUser
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelCreateUser
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelProfileUser
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
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

    @GET("accounts/profile/")
    suspend fun getProfileUser(
        @Header("Content-Type")  content_type:String,
        @Header ("Authorization") authorization:String
    ): ModelProfileUser

    @Multipart
    @PATCH("accounts/profile/")
    suspend fun editProfileUser(
        @Header ("Authorization") authorization:String,
        @Part image : MultipartBody.Part?,
        @Part ("username") username : RequestBody
    )

    @GET("{i18n}/category/")
    suspend fun getCategoryList(
        @Path("i18n") code : String?,
        @Header("Content-Type")  content_type:String,
        @Header ("Authorization") value: String,
        @Query("search") q : String
    ): ModelCategoryRes

    @GET("{i18n}/category/")
    suspend fun getCategoryListDefUser(
        @Path("i18n") code : String?,
        @Header("Content-Type")  content_type:String,
        @Query("search") q : String
    ): ModelCategoryRes

    @GET("{i18n}/words/")
    suspend fun getWordsList(
        @Path("i18n") code : String?,
        @Header("Content-Type")  content_type:String,
        @Header ("Authorization") value: String,
        @Query ("category") categoryId: Int,
        @Query("search") q : String
    ): ModelWordsPag

    @GET("{i18n}/words/")
    suspend fun getWordsListDefUser(
        @Path("i18n") code : String?,
        @Header("Content-Type")  content_type:String,
        @Query ("category") categoryId: Int,
        @Query("search") q : String
    ): ModelWordsPag

    @GET("{i18n}/descriptions/")
    suspend fun getDescriptionsList(
        @Path("i18n") code : String?,
        @Query ("category") categoryId: Int?,
        @Header("Content-Type")  content_type:String?,
        @Header ("Authorization") value: String?,
        @Query("search") q : String?
    ): ModelDescriptionsPag

    @GET("{i18n}/descriptions/")
    suspend fun getDescriptionsListDefUser(
        @Path("i18n") code : String?,
        @Header("Content-Type")  content_type:String,
        @Query ("category") categoryId: Int?,
        @Query("search") q : String?
    ): ModelDescriptionsPag

    @POST("{i18n}/favorites/")
    suspend fun addFavorites(
        @Path("i18n") code : String?,
        @Header ("Authorization") value: String,
        @Header("Content-Type")  content_type:String,
        @Body modelFavorites: ModelFavorites
    )

    @GET("{i18n}/favorites/")
    suspend fun getFavorites(
        @Path("i18n") code : String?,
        @Header ("Authorization") value: String,
        @Header("Content-Type")  content_type:String,
        @Query("search") search : String
    ): ModelFavoritesRes

    @GET("{i18n}/favorites/category/{id}/")
    suspend fun getDescFavorites(
        @Path("i18n") code : String?,
        @Path("id") id : Int,
        @Header ("Authorization") value: String,
        @Header("Content-Type")  content_type:String,
    ): ModelDescFavorites


    companion object {

        private const val BASE_URL = "http://138.68.80.130/"

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