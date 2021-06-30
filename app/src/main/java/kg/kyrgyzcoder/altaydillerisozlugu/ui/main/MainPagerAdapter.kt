package kg.kyrgyzcoder.altaydillerisozlugu.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kg.kyrgyzcoder.altaydillerisozlugu.ui.chosen.favorites.ChosenFragment
import kg.kyrgyzcoder.altaydillerisozlugu.ui.profile.ProfileFragment
import kg.kyrgyzcoder.altaydillerisozlugu.ui.settings.SettingsFragment

class MainPagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment()
            1 -> ChosenFragment()
            2 -> SettingsFragment()
            else -> ProfileFragment()
        }
    }

}