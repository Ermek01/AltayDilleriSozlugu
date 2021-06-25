package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelCategoryRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelDescriptions
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelDescriptionsPag

interface DescriptionsListener {

    fun getDescriptionsSuccess(modelDescriptionsPag: ModelDescriptionsPag)
    fun getDescriptionsError(code: Int?)

}