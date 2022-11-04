package com.shopping.app.data.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @SerializedName("category")
    val category: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("rating")
    val rating: Rating?,
    @SerializedName("title")
    val title: String?
): Parcelable {

    // json convert method
    fun toJson(): String {
        return Gson().toJson(this)
    }

    // static json object
    companion object {
        fun fromJson(jsonValue: String): Product {
            return Gson().fromJson(jsonValue, Product::class.java)
        }
    }

}