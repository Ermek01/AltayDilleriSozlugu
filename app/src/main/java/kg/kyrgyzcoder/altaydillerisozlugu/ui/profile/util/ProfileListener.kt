package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelProfileUser

interface ProfileListener {
    fun getProfileSuccess(modelProfileUser: ModelProfileUser)
    fun getProfileFailure(code: Int?)
}