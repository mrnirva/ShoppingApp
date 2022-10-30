package com.shopping.app.data.repository.product

import com.shopping.app.data.api.ApiService
import com.shopping.app.data.model.Product
import retrofit2.Call

class ProductRepositoryImpl constructor(private val apiService: ApiService) : ProductRepository {

    override fun getProducts(): Call<List<Product>> {
        return apiService.getProducts()
    }

}