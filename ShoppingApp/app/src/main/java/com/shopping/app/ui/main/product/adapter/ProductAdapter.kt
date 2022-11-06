package com.shopping.app.ui.main.product.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.shopping.app.R
import com.shopping.app.data.model.Product
import com.shopping.app.databinding.ItemProductBinding
import com.shopping.app.utils.Constants.PRODUCT_MODEL_NAME

class ProductAdapter (context: Context, productList: List<Product?>, private val navController: NavController) :
    ArrayAdapter<Product?>(context, 0, productList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding:ItemProductBinding

        if (convertView == null || convertView.tag == null) {

            binding = ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        }else{

            binding = convertView.tag as ItemProductBinding

        }

        val product: Product? = getItem(position)
        binding.dataHolder = product
        binding.llProductItem.setOnClickListener {
            goProductDetails(product)
        }

        return binding.root

    }

    private fun goProductDetails(product: Product?){

        navController.navigate(
            R.id.action_homeFragment_to_productDetailsFragment,
            Bundle().apply {
                putString(PRODUCT_MODEL_NAME, product?.toJson())
            })

    }

}