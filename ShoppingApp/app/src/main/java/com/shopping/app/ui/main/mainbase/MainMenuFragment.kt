package com.shopping.app.ui.main.mainbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.shopping.app.R
import com.shopping.app.databinding.FragmentMainMenuBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            bnd.isBottomNavVisible = destination.id != R.id.productDetailsFragment
        }

    }

}