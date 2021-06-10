package kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model

data class ModelCreateUser(
    val username: String,
    val email: String,
    val password: String,
    val password_confirm: String
)
