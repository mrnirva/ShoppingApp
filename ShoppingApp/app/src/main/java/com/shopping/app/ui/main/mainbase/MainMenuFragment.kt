package com.shopping.app.ui.main.mainbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.shopping.app.R
import com.shopping.app.data.repository.basket.BasketRepositoryImpl
import com.shopping.app.databinding.FragmentMainMenuBinding
import com.shopping.app.ui.basket.BasketFragment
import com.shopping.app.ui.basket.viewmodel.BasketViewModel
import com.shopping.app.ui.basket.viewmodel.BasketViewModelFactory

class MainMenuFragment : Fragment() {

    private lateinit var bnd: FragmentMainMenuBinding
    private val viewModel by viewModels<BasketViewModel> {
        BasketViewModelFactory(
            BasketRepositoryImpl()
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false)
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

        bnd.mainMenuFragment = this

        viewModel.basketTotalLiveData.observe(viewLifecycleOwner){
            bnd.total = it
        }

        val navHostFragment = childFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
        NavigationUI.setupWithNavController(bnd.bottomNav, navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            bnd.isBottomNavVisible = destination.id != R.id.productDetailsFragment
        }

    }

    fun openBasket(){

        BasketFragment().show(parentFragmentManager, "basket")

    }

}