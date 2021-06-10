package kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences

class UserPreferencesViewModel(userPreferences: UserPreferences) : ViewModel() {

    val isUserSignedIn = userPreferences.isUserSignedIn

}