package kg.kyrgyzcoder.altaydillerisozlugu.util

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.jakewharton.threetenabp.AndroidThreeTen
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferences
import kg.kyrgyzcoder.altaydillerisozlugu.data.local.UserPreferencesImpl
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.ApiService
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.item.repo.ItemRepository
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.login.repo.AuthRepository
import kg.kyrgyzcoder.altaydillerisozlugu.data.network.user.repo.UserDataRepository
import kg.kyrgyzcoder.altaydillerisozlugu.ui.login.viewmodel.AuthViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.main.viewmodel.ItemViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.viewmodel.ProfileViewModelFactory
import kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.viewmodel.UserPreferencesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.util.*

class App : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {

        import(androidXModule(this@App))

        AndroidThreeTen.init(this@App)

        bind() from singleton { ApiService() }

        bind<UserPreferences>() with singleton { UserPreferencesImpl(instance()) }
        bind() from provider { UserPreferencesViewModelFactory(instance()) }

        bind<AuthRepository>() with singleton { AuthRepository(instance()) }
        bind() from provider { AuthViewModelFactory(instance(), instance()) }

        bind<UserDataRepository>() with singleton { UserDataRepository(instance()) }
        bind() from provider { ProfileViewModelFactory(instance(), instance()) }

        bind<ItemRepository>() with singleton { ItemRepository(instance()) }
        bind() from provider { ItemViewModelFactory(instance(), instance()) }

    }

    companion object {
        var instance: App? = null
        const val PREFS: String = "SHARED_PREFS"
        const val LOCALE : String = "LOCALE"
    }

}