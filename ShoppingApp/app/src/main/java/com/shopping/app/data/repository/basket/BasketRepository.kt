package com.shopping.app.data.repository.basket

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.shopping.app.data.model.Product
import com.shopping.app.data.model.ProductBasket
import retrofit2.Call

interface BasketRepository {

    fun getAllProductsBasket(): CollectionReference

    fun getTargetProductsBasket(productBasket: ProductBasket): DocumentReference

    fun addProductsToBasket(productBasket: ProductBasket): Task<Void>

    fun deleteProducts(productBasket: ProductBasket): Task<Void>

    fun updateProductsPiece(productBasket: ProductBasket): Task<Void>

}