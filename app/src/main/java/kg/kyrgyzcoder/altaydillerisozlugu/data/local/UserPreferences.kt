package kg.kyrgyzcoder.altaydillerisozlugu.data.local

import kotlinx.coroutines.flow.Flow

interface UserPreferences {

    val isUserSignedIn: Flow<String?>
    val currentUserUsername: Flow<String?>
    val currentUserEmail: Flow<String?>
    val currentUserToken: Flow<String?>

    suspend fun logoutUser()

    suspend fun saveUserData(
        token : String)
}