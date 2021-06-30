package kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ModelDescriptions(
    val id: Int,
    val image: String,
    val category: String,
    val favorite: Boolean,
    val languages: List<Languages>
)