package com.teb.wordpressapp.ui.screen.main.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teb.wordpressapp.data.model.Category
import com.teb.wordpressapp.databinding.ListItemCategoryBinding

typealias OnCategoriesItemClickListener = (category: Category) -> Unit

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoriesViewHolder>() {

    var categoryItemList = mutableListOf<Category>()

    var postItemTitleClickListener: OnCategoriesItemClickListener? = null

    fun setDataList(dataList: List<Category>) {
        categoryItemList.clear()
        categoryItemList.addAll(dataList)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val binding =
            ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoriesViewHolder(binding);
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val categoryItem = categoryItemList.get(position)

        holder.binding.apply {
            categoryName.text = categoryItem.name
        }
    }

    override fun getItemCount(): Int {
        return categoryItemList.size
    }

    inner class CategoriesViewHolder(var binding: ListItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val clickedPostItem = categoryItemList.get(adapterPosition)
                postItemTitleClickListener?.invoke(clickedPostItem)
            }
        }

    }
}