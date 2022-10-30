package com.shopping.app.data.repository.product

import com.shopping.app.data.model.Product
import retrofit2.Call

interface ProductRepository {

    fun getProducts(): Call<List<Product>>

}