package kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences

class UserPreferencesViewModelFactory(
    private val userPreferences: UserPreferences
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserPreferencesViewModel(userPreferences) as T
    }

}