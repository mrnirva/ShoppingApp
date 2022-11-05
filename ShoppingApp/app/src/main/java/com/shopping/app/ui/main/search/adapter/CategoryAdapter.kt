package com.shopping.app.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shopping.app.data.model.CategoryModel
import com.shopping.app.databinding.ItemCategoryBinding
import com.shopping.app.ui.main.search.CategoryClickListener

class CategoryAdapter(
      private val categoryList: List<CategoryModel>,
      val listener: CategoryClickListener
) : RecyclerView.Adapter<CategoryAdapter.SearchViewHolder>() {

    private lateinit var binding: ItemCategoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bind(category)
    }

    inner class SearchViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryModel) {
            binding.dataHolder = category
            binding.categoryAdapter = this@CategoryAdapter
            binding.executePendingBindings()
        }

    }

    override fun getItemCount(): Int = categoryList.size

}