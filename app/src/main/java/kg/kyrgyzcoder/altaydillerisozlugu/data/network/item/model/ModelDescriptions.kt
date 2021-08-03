package kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ModelDescriptions(
    val id: Int,
    val image: String,
    val category: String,
    val favorite: Boolean,
    val languages: List<Languages>
)