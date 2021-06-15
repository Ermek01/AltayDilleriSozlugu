package kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.model

data class ModelLoginRes(
    val token : String,
    val user_id : Int,
    val username : String,
    val email : String
)
