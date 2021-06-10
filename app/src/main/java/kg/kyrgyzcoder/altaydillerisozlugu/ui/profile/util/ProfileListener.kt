package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util

interface ProfileListener {

    fun logoutSuccess()
    fun logoutFail(code: Int?)

}