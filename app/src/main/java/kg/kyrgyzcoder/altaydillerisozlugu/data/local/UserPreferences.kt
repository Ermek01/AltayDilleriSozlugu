package kg.kyrgyzcoder.altaydillerisozlugu.data.local

import kotlinx.coroutines.flow.Flow

interface UserPreferences {

    val isUserSignedIn: Flow<String?>
    val currentUserId: Flow<Int?>
    val currentUserUsername: Flow<String?>
    val currentUserEmail: Flow<String?>
    val currentUserToken: Flow<String?>
    //val currentLanguages: Flow<List<List<String?>>?>

    suspend fun logoutUser()

    suspend fun saveUserData(
        token : String, user_id : Int, username: String, email: String)
}