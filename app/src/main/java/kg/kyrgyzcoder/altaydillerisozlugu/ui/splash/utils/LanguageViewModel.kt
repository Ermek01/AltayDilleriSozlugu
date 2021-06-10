package kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils

import kg.kyrgyzcoder.altaydillerisozlugu.R

class LanguageViewModel {

    fun loadLanguage(): ArrayList<Language> {
        return arrayListOf(
            Language(R.drawable.tr, "Türkçe", "tr"),
            Language(R.drawable.az, "Azərbaycan", "az"),
            Language(R.drawable.uz, "O'zbek", "uz"),
            Language(R.drawable.kz, "Казакша", "kz"),
            Language(R.drawable.ug, "Uygurçi", "ug"),
            Language(R.drawable.tm, "Türkmen", "tm"),
            Language(R.drawable.tatar, "Татар", "tatar"),
            Language(R.drawable.kg, "Кыргызча", "kg"),
            Language(R.drawable.ba, "Башҡорт", "ru-ba"),
            Language(R.drawable.cu, "Чăваш", "ru-cu"),
            Language(R.drawable.qr, "Qaraqalpaq", "uz-qr"),
        )
    }

}