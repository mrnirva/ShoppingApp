package com.shopping.app.ui.main.product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.shopping.app.data.model.Product
import com.shopping.app.databinding.ItemProductBinding


class ProductAdapter (context: Context, productList: List<Product?>) :
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

        return binding.root

    }

}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).into(view)
    }
}