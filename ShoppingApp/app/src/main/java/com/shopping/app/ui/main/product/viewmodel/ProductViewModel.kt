package com.shopping.app.ui.main.product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shopping.app.data.model.DataState
import com.shopping.app.data.model.Product
import com.shopping.app.data.repository.product.ProductRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private var _productLiveData = MutableLiveData<DataState<List<Product>?>>()
    val productLiveData: LiveData<DataState<List<Product>?>>
        get() = _productLiveData

    init {
        getProducts()
    }

    private fun getProducts(){

        _productLiveData.postValue(DataState.Loading())
        productRepository.getProducts().enqueue(object: Callback<List<Product>>{

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                if (response.isSuccessful) {
                    response.body()?.let {
                        _productLiveData.postValue(DataState.Success(it))
                    } ?: kotlin.run {
                        _productLiveData.postValue(DataState.Error("Data Empty"))
                    }
                } else {
                    _productLiveData.postValue(DataState.Error(response.message()))
                }

            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                _productLiveData.postValue(DataState.Error(t.message.toString()))
            }

        })

    }

}