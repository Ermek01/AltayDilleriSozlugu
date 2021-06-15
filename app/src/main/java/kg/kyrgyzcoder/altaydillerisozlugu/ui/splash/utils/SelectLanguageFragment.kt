package kg.kyrgyzcoder.altaydillerisozlugu.ui.splash.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.DialogFragment
import kg.kyrgyzcoder.altaydillerisozlugu.R
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.ActivitySplashScreenBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentMainBinding
import kg.kyrgyzcoder.altaydillerisozlugu.databinding.FragmentSelectLanguageBinding
import kg.kyrgyzcoder.altaydillerisozlugu.util.LANGUAGE_KEY
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import java.util.*


class SelectLanguageFragment(private val listener: LanguageListener) : DialogFragment(), KodeinAware, CountryAdapter.OnItemClickListener {

    override val kodein: Kodein by closestKodein()

    private var _binding: FragmentSelectLanguageBinding? = null
    private val binding: FragmentSelectLanguageBinding get() = _binding!!

    private val countryList = addItemList(14)
    private var isTouched: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scrol.isFillViewport = true

        binding.recyclerView.adapter = CountryAdapter(countryList, this)
        binding.recyclerView.setHasFixedSize(true)



    }

    fun addItemList(size: Int) : List<Language> {

        val list = ArrayList<Language>()



        for (i in 0 until size) {
            val drawable = when (i % 14) {
                0 -> R.drawable.tr
                1 -> R.drawable.az
                2 -> R.drawable.uz
                3 -> R.drawable.kz
                4 -> R.drawable.ug
                5 -> R.drawable.tm
                6 -> R.drawable.tatar
                7 -> R.drawable.kg
                8 -> R.drawable.ba
                9 -> R.drawable.cu
                10 -> R.drawable.qr
                11 -> R.drawable.krc
                12 -> R.drawable.sah
                else -> R.drawable.crh
            }

            val name = when (i % 14) {
                0 -> "Türkçe"
                1 -> "Azərbaycan"
                2 -> "O'zbek"
                3 -> "Казакша"
                4 -> "Uygurçi"
                5 -> "Türkmen"
                6 -> "Татар"
                7 -> "Кыргызча"
                8 -> "Башҡорт"
                9 -> "Чăваш"
                10 -> "Qaraqalpaq"
                11 -> "Карачай-балкар"
                12 -> "Якутия"
                else -> "Крым Татарский"
            }

            val code = when (i % 14) {
                0 -> "tr"
                1 -> "az"
                2 -> "uz"
                3 -> "kk"
                4 -> "ug"
                5 -> "tk"
                6 -> "tt"
                7 -> "ky"
                8 -> "ba"
                9 -> "cv"
                10 -> "kaa"
                11 -> "krc"
                12 -> "sah"
                else -> "crh"
            }

            val item = Language(drawable, name, code)
            list += item
        }
        return list

    }


    override fun onItemClick(position: Int) {
        listener.getLanguage(countryList[position].flag, countryList[position].name, countryList[position].code, true)
        setLocale(position)
        recreate(requireActivity())
        dismiss()
    }

    @SuppressLint("CommitPrefEdits")
    private fun setLocale(position: Int) {

        val locale = Locale(countryList[position].code)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

//        val config = resources.configuration
//        val locale = Locale(countryList[position].code)
//        Locale.setDefault(locale)
//        config.locale = locale
//        resources.updateConfiguration(config, resources.displayMetrics)
//        recreate(requireActivity())
    }
}