package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.repo.ItemRepository

class ItemViewModelFactory(
    private val userPreferences: UserPreferences,
    private val itemRepository: ItemRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemViewModel(userPreferences, itemRepository) as T
    }

}