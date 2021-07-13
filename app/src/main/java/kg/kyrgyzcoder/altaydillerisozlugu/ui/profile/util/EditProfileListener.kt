package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util

import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.model.ModelProfileUser

interface EditProfileListener {
    fun editProfileSuccess()
    fun editProfileFailure(code: Int?)
}