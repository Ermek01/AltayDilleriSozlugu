package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.NetworkResponse
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavorites
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.repo.FavoriteRepository
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.repo.ItemRepository
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.model.ModelLoginUser
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils.FavoriteListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.login.AuthListener
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils.CategoryListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChosenViewModel(
    private val userPreferences: UserPreferences,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {

    val userId = userPreferences.currentUserId

    private var favoriteListener: FavoriteListener? = null

    fun addFavoriteListener(favoriteListener: FavoriteListener) {
        this.favoriteListener = favoriteListener
    }

    fun addFavorite(code: String?, modelFavorites: ModelFavorites) = viewModelScope.launch {
        userPreferences.currentUserToken.collectLatest { token ->
            if (token!!.isNotEmpty()){
                when (val response = favoriteRepository.addFavorites(code,"Token $token", modelFavorites)) {
                    is NetworkResponse.Success -> {
                        favoriteListener?.addFavoriteSuccess()
                    }
                    is NetworkResponse.Failure -> {
                        favoriteListener?.addFavoriteFailure(response.errorCode)
                    }
                }
            }

        }


    }

}