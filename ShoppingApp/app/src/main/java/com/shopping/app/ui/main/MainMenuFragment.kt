package com.shopping.app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.shopping.app.R
import com.shopping.app.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment() {

    private lateinit var bnd: FragmentMainMenuBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false)
        init()
        return bnd.root

    }

    private fun init(){

        val navHostFragment = childFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
        NavigationUI.setupWithNavController(bnd.bottomNav, navHostFragment.navController)

    }

}