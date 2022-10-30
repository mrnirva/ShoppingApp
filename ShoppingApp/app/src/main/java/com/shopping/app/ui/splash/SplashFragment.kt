package com.shopping.app.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.shopping.app.R
import com.shopping.app.databinding.FragmentOnboardingBinding
import com.shopping.app.databinding.FragmentSplashBinding
import com.shopping.app.ui.onboarding.adapter.OnboardAdapter
import com.shopping.app.ui.onboarding.viewmodel.OnboardViewModel

class SplashFragment : Fragment() {

    private lateinit var bnd: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        init()

        return bnd.root
    }

    private fun init(){

        findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)

    }

}