package com.shopping.app.ui.auth.authbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.shopping.app.R
import com.shopping.app.databinding.FragmentAuthBaseBinding
import com.shopping.app.ui.auth.authbase.adapter.AuthTabLayoutAdapter
import com.shopping.app.ui.auth.authbase.viewmodel.AuthBaseViewModel

class AuthBaseFragment : Fragment() {

    private lateinit var bnd: FragmentAuthBaseBinding
    private val viewModel: AuthBaseViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_auth_base, container, false)
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

        viewModel.fragmentLiveData.observe(viewLifecycleOwner){

            it.let {  safeData ->

                val adapter = AuthTabLayoutAdapter(safeData.fragmentList, this@AuthBaseFragment)
                bnd.viewPager.adapter = adapter

                TabLayoutMediator(bnd.tabLayout, bnd.viewPager){ tab, position ->
                    tab.text = getString(safeData.titleList[position])
                }.attach()

            }

        }

    }

}