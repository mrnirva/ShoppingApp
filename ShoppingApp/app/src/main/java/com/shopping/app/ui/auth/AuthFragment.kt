package com.shopping.app.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.shopping.app.R
import com.shopping.app.databinding.FragmentAuthBinding
import com.shopping.app.ui.auth.adapter.AuthTabLayoutAdapter

class AuthFragment : Fragment() {

    private lateinit var bnd: FragmentAuthBinding
    private val fragmentList = ArrayList<Fragment>()
    private val fragmentTitleList = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
        init()
        return bnd.root

    }

    private fun init(){

        fragmentList.add(SignInFragment())
        fragmentList.add(SignUpFragment())

        fragmentTitleList.add(resources.getString(R.string.signin))
        fragmentTitleList.add(resources.getString(R.string.signup))

        val adapter = AuthTabLayoutAdapter(fragmentList, this@AuthFragment)
        bnd.viewPager.adapter = adapter

        TabLayoutMediator(bnd.tabLayout, bnd.viewPager){ tab, position ->
            tab.text = fragmentTitleList[position]
        }.attach()

    }

}