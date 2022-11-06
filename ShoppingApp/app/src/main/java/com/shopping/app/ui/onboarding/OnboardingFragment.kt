package com.shopping.app.ui.onboarding

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
import com.shopping.app.data.preference.UserPref
import com.shopping.app.databinding.FragmentOnboardingBinding
import com.shopping.app.ui.onboarding.adapter.OnboardAdapter
import com.shopping.app.ui.onboarding.viewmodel.OnboardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingFragment : Fragment(), OnboardButtonListener {

    private lateinit var bnd: FragmentOnboardingBinding
    private val viewModel: OnboardViewModel by viewModels()

    private lateinit var onboardAdapter: OnboardAdapter
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding, container, false)
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

        bnd.onboardingFragment = this

        viewModel.boardingLiveData.observe(viewLifecycleOwner){
            it.let {
                onboardAdapter = OnboardAdapter(it)
                bnd.viewPagerOnboard.adapter = onboardAdapter
                bnd.viewPagerOnboard.registerOnPageChangeCallback(pageChangeListener)
                indicators()
                setCurrentIndicators(0)
            }
        }


        pageChangeListener = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                setCurrentIndicators(position)

                bnd.prevButtonVisibility = position != 0

                bnd.nextButtonText = if(position == onboardAdapter.itemCount-1) resources.getString(R.string.finish)
                else resources.getString(R.string.next)

            }
        }

    }

    private fun indicators(){

        val indicators = arrayOfNulls<ImageView>(onboardAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)

        for(i in indicators.indices){
            indicators[i] = ImageView(requireContext().applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.bg_indicator_inactive
                    )
                )
                it.layoutParams = layoutParams
                bnd.indicatorsContainer.addView(it)
            }
        }

    }

    private fun setCurrentIndicators(position: Int){

        val childCount = bnd.indicatorsContainer.childCount
        for(i in 0 until childCount){
            val imageView = bnd.indicatorsContainer.getChildAt(i) as ImageView
            if(i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.bg_indicator_active
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.bg_indicator_inactive
                    )
                )
            }
        }

    }

    override fun prevClickListener(){
        if(bnd.viewPagerOnboard.currentItem > 0) bnd.viewPagerOnboard.currentItem -= 1
    }

    override fun nextClickListener(){
        if(bnd.viewPagerOnboard.currentItem + 1 < onboardAdapter.itemCount) bnd.viewPagerOnboard.currentItem += 1
        else nextScreen()
    }

    fun nextScreen(){

        val userPref = UserPref(requireContext())
        CoroutineScope(Dispatchers.Main).launch {

            userPref.setFirstUsage(false)
            findNavController().navigate(R.id.action_onboardingFragment_to_authFragment)

        }


    }

}

interface OnboardButtonListener{

    fun prevClickListener()
    fun nextClickListener()

}