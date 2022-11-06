package com.shopping.app.data.repository.basket

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shopping.app.data.model.ProductBasket
import com.shopping.app.utils.Constants
import com.shopping.app.utils.Constants.DATABASE_PRODUCTS_TABLE_PIECE_FIELD

class BasketRepositoryImpl : BasketRepository {

    override fun getAllProductsBasket(): CollectionReference {

        return Firebase.firestore.collection(Constants.DATABASE_BASKET_TABLE)
            .document(FirebaseAuth.getInstance().uid!!)
            .collection(Constants.DATABASE_PRODUCTS_TABLE)

    }

    override fun getTargetProductsBasket(productBasket: ProductBasket): DocumentReference {

        return Firebase.firestore.collection(Constants.DATABASE_BASKET_TABLE)
            .document(FirebaseAuth.getInstance().uid!!)
            .collection(Constants.DATABASE_PRODUCTS_TABLE)
            .document(productBasket.id.toString())

    }

    override fun addProductsToBasket(productBasket: ProductBasket): Task<Void> {

        return Firebase.firestore.collection(Constants.DATABASE_BASKET_TABLE)
            .document(FirebaseAuth.getInstance().uid!!)
            .collection(Constants.DATABASE_PRODUCTS_TABLE)
            .document(productBasket.id.toString())
            .set(productBasket)

    }

    override fun deleteProducts(productBasket: ProductBasket): Task<Void> {

        return Firebase.firestore.collection(Constants.DATABASE_BASKET_TABLE)
            .document(FirebaseAuth.getInstance().uid!!)
            .collection(Constants.DATABASE_PRODUCTS_TABLE)
            .document(productBasket.id.toString())
            .delete()

    }

    override fun updateProductsPiece(productBasket: ProductBasket): Task<Void> {

        return Firebase.firestore.collection(Constants.DATABASE_BASKET_TABLE)
            .document(FirebaseAuth.getInstance().uid!!)
            .collection(Constants.DATABASE_PRODUCTS_TABLE)
            .document(productBasket.id.toString())
            .update(DATABASE_PRODUCTS_TABLE_PIECE_FIELD, productBasket.piece)

    }

}