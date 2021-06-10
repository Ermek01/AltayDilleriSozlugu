package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelCategoryRes

interface CategoryListener {

    fun getCategories(modelCategoryRes: ModelCategoryRes)
    fun getCategoryError(code: Int?)

}