package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavoritesRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelFavoritesResItem

interface GetFavoriteListener {

    fun getFavoritesSuccess(modelFavoritesRes: ModelFavoritesRes)
    fun getFavoritesError(code: Int?)

}