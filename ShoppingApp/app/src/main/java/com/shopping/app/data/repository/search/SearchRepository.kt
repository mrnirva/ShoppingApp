package com.shopping.app.data.repository.search

import com.shopping.app.data.model.Product
import retrofit2.Call

interface SearchRepository {

    fun getProducts(): Call<List<Product>>

}