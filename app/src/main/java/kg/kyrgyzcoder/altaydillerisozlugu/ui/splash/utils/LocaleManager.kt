package kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils

import android.content.Context
import android.content.res.Configuration
import kg.kyrgyzcoder.altaydillerisozlugu.util.App
import java.util.*
import kotlin.coroutines.Continuation

object LocaleManager {

    fun setLocale(context: Context) : Context {
        return if (App.instance!!.getLanguagePref() != null) {
            updateResources(context, App.instance!!.getLanguagePref()!!)
        }
        else{
            context
        }
    }

    fun setNewLocale(context: Context, language: String) : Context {
        App.instance!!.setLanguagePref(language)
        return updateResources(context, language)
    }

    private fun updateResources(context: Context, language: String) : Context {
        var localeContext = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        localeContext = context.createConfigurationContext(config)
        return localeContext
    }

}