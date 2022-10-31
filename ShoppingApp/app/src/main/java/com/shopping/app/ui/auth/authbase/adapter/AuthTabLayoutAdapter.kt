package com.shopping.app.ui.auth.authbase.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AuthTabLayoutAdapter (
    private val fragmentList: List<Fragment>,
    fragment: Fragment
    ) : FragmentStateAdapter(fragment){

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

}