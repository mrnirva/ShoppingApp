package com.shopping.app.ui.auth.authbase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shopping.app.R
import com.shopping.app.data.model.TabLayoutFragment
import com.shopping.app.ui.auth.signin.SignInFragment
import com.shopping.app.ui.auth.signup.SignUpFragment

class AuthBaseViewModel : ViewModel() {

    val fragmentLiveData = MutableLiveData<TabLayoutFragment>()

    init {
        initFragments()
    }

    private fun initFragments(){

        val fragments = TabLayoutFragment(
            listOf(
                SignInFragment(),
                SignUpFragment()
            ),
            listOf(
                R.string.signin,
                R.string.signup
            )
        )

        fragmentLiveData.value = fragments

    }

}