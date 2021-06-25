package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.repo.FavoriteRepository
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.repo.ItemRepository

class ChosenViewModelFactory(
    private val userPreferences: UserPreferences,
    private val favoriteRepository: FavoriteRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChosenViewModel(userPreferences, favoriteRepository) as T
    }

}