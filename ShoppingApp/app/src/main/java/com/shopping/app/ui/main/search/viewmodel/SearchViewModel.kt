package com.shopping.app.ui.main.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shopping.app.data.model.DataState
import com.shopping.app.data.model.Product
import com.shopping.app.data.repository.product.ProductRepository
import com.shopping.app.data.repository.search.SearchRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private lateinit var productList:List<Product>
    private var _searchLiveData = MutableLiveData<DataState<List<Product>?>>()
    val searchLiveData: LiveData<DataState<List<Product>?>>
        get() = _searchLiveData

    init {
        getProducts()
    }

    private fun getProducts(){

        _searchLiveData.postValue(DataState.Loading())
        searchRepository.getProducts().enqueue(object: Callback<List<Product>>{

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {

                if (response.isSuccessful) {
                    response.body()?.let {

                        productList = it
                        _searchLiveData.postValue(DataState.Success(productList))

                    } ?: kotlin.run {
                        _searchLiveData.postValue(DataState.Error("Data Empty"))
                    }
                } else {
                    _searchLiveData.postValue(DataState.Error(response.message()))
                }

            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                _searchLiveData.postValue(DataState.Error(t.message.toString()))
            }

        })

    }

    fun searchProducts(isSearch:Boolean = false, query:String = ""){

        if(productList.isNotEmpty()){

            if(isSearch){

                val searchList = productList.filter {
                    it.title!!.lowercase().contains(query) ||
                            it.description!!.lowercase().contains(query) ||
                            it.category!!.lowercase().contains(query)
                }

                _searchLiveData.postValue(DataState.Success(searchList))

            }else{
                _searchLiveData.postValue(DataState.Success(productList))
            }

        }else{
            getProducts()
        }

    }

}