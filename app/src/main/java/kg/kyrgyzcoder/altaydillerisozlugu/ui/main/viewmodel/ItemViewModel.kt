package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.NetworkResponse
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.repo.ItemRepository
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.CategoryListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.DescriptionsListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.WordsListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class ItemViewModel(
    private val userPreferences: UserPreferences,
    private val itemRepository: ItemRepository
) : ViewModel() {


    private var listener: CategoryListener? = null
    private var wordsListener: WordsListener? = null
    private var descriptionsListener: DescriptionsListener? = null

    fun getCategoryListener(listener: CategoryListener) {
        this.listener = listener
    }

    fun getWordsListener(wordsListener: WordsListener) {
        this.wordsListener = wordsListener
    }

    fun getDescriptionsListener(descriptionsListener: DescriptionsListener) {
        this.descriptionsListener = descriptionsListener
    }



    fun getCategoryList(code: String?,search: String) = viewModelScope.launch {

        userPreferences.currentUserToken.collectLatest { token ->
            if (token!!.isNotEmpty()) {
                when (val response = itemRepository.getCategoryList(code,"Token $token", search)) {
                    is NetworkResponse.Success -> {
                        listener?.getCategories(response.value)
                    }
                    is NetworkResponse.Failure -> {
                        listener?.getCategoryError(response.errorCode)
                    }
                }
            }
            else {
                when (val response = itemRepository.getCategoryListDefUser(code, search)) {
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

    fun getWordsList(code: String?,categoryId: Int, search: String) = viewModelScope.launch {

        userPreferences.currentUserToken.collectLatest { token ->
            if (token!!.isNotEmpty()) {
                when (val response = itemRepository.getWordsList(code,"Token $token", categoryId, search)) {
                    is NetworkResponse.Success -> {
                        wordsListener?.getWordsSuccess(response.value)
                    }
                    is NetworkResponse.Failure -> {
                        wordsListener?.getWordsError(response.errorCode)
                    }
                }
            }
            else {
                when (val response = itemRepository.getWordsListDefUser(code, categoryId, search)) {
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

    fun getDescriptionsList(code: String?,categoryId: Int) = viewModelScope.launch {

        userPreferences.currentUserToken.collectLatest { token ->
            if (token!!.isNotEmpty()) {
                when (val response = itemRepository.getDescriptionsList(code,"Token $token", categoryId)) {
                    is NetworkResponse.Success -> {
                        descriptionsListener?.getDescriptionsSuccess(response.value)
                    }
                    is NetworkResponse.Failure -> {
                        descriptionsListener?.getDescriptionsError(response.errorCode)
                    }
                }
            }
            else {
                when (val response = itemRepository.getDescriptionsListDefUser(code, categoryId)) {
                    is NetworkResponse.Success -> {
                        descriptionsListener?.getDescriptionsSuccess(response.value)
                    }
                    is NetworkResponse.Failure -> {
                        descriptionsListener?.getDescriptionsError(response.errorCode)
                    }
                }
            }
        }

    }

}