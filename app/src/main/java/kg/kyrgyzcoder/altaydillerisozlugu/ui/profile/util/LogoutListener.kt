package kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.util

interface LogoutListener {

    fun logoutSuccess()
    fun logoutFail(code: Int?)

}