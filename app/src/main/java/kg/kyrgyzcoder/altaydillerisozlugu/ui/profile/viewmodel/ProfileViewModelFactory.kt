package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.repo.UserDataRepository

class ProfileViewModelFactory(
    private val userPreferences: UserPreferences,
    private val userDataRepository: UserDataRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(userPreferences, userDataRepository) as T
    }

}