package com.example.sjr_pringles.Project

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sjr_pringles.Project.Home.HomeFragment
import com.example.sjr_pringles.Project.Log.LogFragment
import com.example.sjr_pringles.Project.Menu.MenuFragment
import com.example.sjr_pringles.Project.Profile.ProfileFragment

class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> MenuFragment()
            2 -> LogFragment()
            3 -> ProfileFragment()
            else -> HomeFragment()
        }
    }
}