package kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ModelDescriptions(
    val image: String,
    val category: String,
    val languages: List<Languages>
)