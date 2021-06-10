package kg.kyrgyzcoder.altaydillerisozlugu.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesImpl(context: Context) : UserPreferences {

    private val applicationContext = context.applicationContext

    private val dataStore: DataStore<Preferences> = applicationContext.createDataStore(
        name = "user_data"
    )

    override suspend fun saveUserData(token: String ) {
        dataStore.edit { prefs ->
            prefs[IS_USER_SIGNED_IN] = "is_user_signed_in"
            prefs[USER_TOKEN_KEY] = token
        }
    }

    override val isUserSignedIn: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[IS_USER_SIGNED_IN]
        }

    override val currentUserUsername: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[USER_USERNAME_KEY]
        }

    override val currentUserEmail: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[USER_EMAIL_KEY]
        }

    override val currentUserToken: Flow<String?>
        get() = dataStore.data.map { pref ->
            pref[USER_TOKEN_KEY]
        }

    override suspend fun logoutUser() {
        dataStore.edit { prefs ->
            prefs[IS_USER_SIGNED_IN] = ""
        }
    }


    companion object {
        private val IS_USER_SIGNED_IN = stringPreferencesKey("is_user_signed_in")
        private val USER_USERNAME_KEY = stringPreferencesKey("user_username_key")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email_key")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token_key")

    }


}