package com.shopping.app.ui.main.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shopping.app.data.model.Product
import com.shopping.app.databinding.ItemProductSearchBinding

class SearchAdapter(
    private val context:Context,
    private val productList: List<Product>
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var binding: ItemProductSearchBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = ItemProductSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productList.size

    inner class SearchViewHolder(private val binding: ItemProductSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.dataHolder = product
        }
    }


}