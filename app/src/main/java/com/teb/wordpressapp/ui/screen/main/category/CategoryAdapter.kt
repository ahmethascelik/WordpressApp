package com.teb.wordpressapp.ui.screen.main.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.teb.wordpressapp.data.model.Category
import com.teb.wordpressapp.databinding.ListItemCategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var categoryItemList = mutableListOf<Category>()

    fun setDataList(dataList: List<Category>) {
        categoryItemList.clear()
        categoryItemList.addAll(dataList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ListItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding);
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryItem = categoryItemList.get(position)

        holder.binding.apply {
            categoryName.text = categoryItem.name
        }
    }

    override fun getItemCount(): Int {
        return categoryItemList.size
    }

    inner class CategoryViewHolder(var binding: ListItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val clickedCategoryItem = categoryItemList[adapterPosition]
                Toast.makeText(itemView.context, "This category will not be shown now :) I can show it after learning more Android :)", Toast.LENGTH_SHORT).show()
            }
        }
    }

}