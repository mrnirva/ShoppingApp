package com.shopping.app.ui.productdetail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shopping.app.R
import com.shopping.app.data.model.OnBoardModel

class ProductDetailViewModel : ViewModel() {

    val productCountLiveData = MutableLiveData<Int>()

    init {
        setDefaultCount()
    }

    private fun setDefaultCount(){

        productCountLiveData.value = 1

    }

    fun reduceCount(value:Int){

        if(value > 1) productCountLiveData.value = value-1

    }

    fun increaseCount(value:Int){

        if(value < 100) productCountLiveData.value = value+1

    }

}