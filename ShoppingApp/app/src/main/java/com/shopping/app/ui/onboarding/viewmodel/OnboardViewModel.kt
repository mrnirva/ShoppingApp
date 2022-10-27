package com.shopping.app.ui.onboarding.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shopping.app.R
import com.shopping.app.data.model.OnBoardModel

class OnboardViewModel : ViewModel() {

    val boardingLiveData = MutableLiveData<List<OnBoardModel>>()

    init {
        getOnboardData()
    }

    private fun getOnboardData(){

        boardingLiveData.postValue(

            listOf(
                OnBoardModel(
                    R.drawable.boarding_image_1,
                    "Title 1",
                    "Description 1"
                ),
                OnBoardModel(
                    R.drawable.boarding_image_2,
                    "Title 2",
                    "Description 2"
                ),
                OnBoardModel(
                    R.drawable.boarding_image_3,
                    "Title 3",
                    "Description 3"
                )
            )

        )

    }

}