package com.shopping.app.data.api

import com.shopping.app.data.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("products")
    fun getProducts(): Call<List<Product>>

}