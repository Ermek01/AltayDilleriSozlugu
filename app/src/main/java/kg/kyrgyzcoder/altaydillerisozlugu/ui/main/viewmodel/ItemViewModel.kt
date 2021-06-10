package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.NetworkResponse
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.repo.ItemRepository
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.CategoryListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.WordsListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ItemViewModel(
    private val userPreferences: UserPreferences,
    private val itemRepository: ItemRepository
) : ViewModel() {

    private var listener: CategoryListener? = null
    private var wordsListener: WordsListener? = null

    fun getCategoryListener(listener: CategoryListener) {
        this.listener = listener
    }

    fun getWordsListener(wordsListener: WordsListener) {
        this.wordsListener = wordsListener
    }

    fun getCategoryList(search: String) = viewModelScope.launch {

        userPreferences.currentUserToken.collectLatest { token ->
            if (token != null) {
                when (val response = itemRepository.getCategoryList("Token $token", search)) {
                    is NetworkResponse.Success -> {
                        listener?.getCategories(response.value)
                    }
                    is NetworkResponse.Failure -> {
                        listener?.getCategoryError(response.errorCode)
                    }
                }
            }
            else {
                when (val response = itemRepository.getCategoryListDefUser(search)) {
                    is NetworkResponse.Success -> {
                        listener?.getCategories(response.value)
                    }
                    is NetworkResponse.Failure -> {
                        listener?.getCategoryError(response.errorCode)
                    }
                }
            }
        }

    }

    fun getWordsList(categoryId: Int, search: String) = viewModelScope.launch {

        userPreferences.currentUserToken.collectLatest { token ->
            if (token != null) {
                when (val response = itemRepository.getWordsList("Token $token", categoryId, search)) {
                    is NetworkResponse.Success -> {
                        wordsListener?.getWordsSuccess(response.value)
                    }
                    is NetworkResponse.Failure -> {
                        wordsListener?.getWordsError(response.errorCode)
                    }
                }
            }
            else {
                when (val response = itemRepository.getWordsListDefUser(categoryId, search)) {
                    is NetworkResponse.Success -> {
                        wordsListener?.getWordsSuccess(response.value)
                    }
                    is NetworkResponse.Failure -> {
                        wordsListener?.getWordsError(response.errorCode)
                    }
                }
            }
        }

    }

}