package kg.kyrgyzcoder.altaydillerisozlugu.ui.login

interface AuthListener {

    fun loginFailed(code: Int?)
    fun userDataSaved()

}