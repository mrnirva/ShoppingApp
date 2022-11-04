package com.shopping.app.data.repository.search

import com.shopping.app.data.api.ApiService
import com.shopping.app.data.model.Product
import retrofit2.Call

class SearchRepositoryImpl constructor(private val apiService: ApiService) : SearchRepository {

    override fun getProducts(): Call<List<Product>> {
        return apiService.getProducts()
    }

}