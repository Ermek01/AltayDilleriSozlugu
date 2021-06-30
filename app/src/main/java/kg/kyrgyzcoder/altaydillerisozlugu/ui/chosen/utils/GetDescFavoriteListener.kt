package kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.utils

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.favorites.model.ModelDescFavorites

interface GetDescFavoriteListener {

    fun getDescFavoritesSuccess(modelDescFavorites: ModelDescFavorites)
    fun getDescFavoritesError(code: Int?)

}