package kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model

import android.net.Uri
import okhttp3.MultipartBody
import java.io.File

data class ModelEditProfile(
    val username: String,
    val image : File?
)
