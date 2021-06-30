package kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model

data class ModelDescFavoritesItem(
    val category: String,
    val favorite: Boolean,
    val id: Int,
    val image: String,
    val languages: List<Language>
)