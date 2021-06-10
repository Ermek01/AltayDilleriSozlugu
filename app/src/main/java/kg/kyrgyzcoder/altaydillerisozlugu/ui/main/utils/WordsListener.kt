package kg.kyrgyzcoder.altaydillerisozlugu.ui.main.utils

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelCategoryRes
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.model.ModelWordsPag

interface WordsListener {

    fun getWordsSuccess(modelWordsPag: ModelWordsPag)
    fun getWordsError(code: Int?)

}