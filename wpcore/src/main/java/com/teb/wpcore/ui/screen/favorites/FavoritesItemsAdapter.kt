package com.teb.wpcore.ui.screen.favorites

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.htmlEncode
import androidx.recyclerview.widget.RecyclerView
import com.teb.wpcore.data.model.Comment
import com.teb.wpcore.data.model.PostItem
import com.teb.wpcore.databinding.ListItemPostBinding
import com.teb.wpcore.ui.screen.main.postitem.OnPostItemClickListener
import com.teb.wpcore.ui.util.loadUrl

class FavoritesItemsAdapter : RecyclerView.Adapter<FavoritesItemsAdapter.PostItemViewHolder>() {

    var postItemList = mutableListOf<PostItem>()

    var postItemTitleClickListener: OnPostItemClickListener? = null

    fun setDataList(dataList: List<PostItem>) {
        postItemList.clear()
        postItemList.addAll(dataList)
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {

        val binding =
            ListItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PostItemViewHolder(binding);
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val postItem = postItemList.get(position)

        holder.binding.apply {
            txtTitle.text = postItem.title()
            txtExcerpt.text = postItem.description()
            image.loadUrl(postItem.imageUrl())
        }

    }

    override fun getItemCount(): Int {
        return postItemList.size
    }


    inner class PostItemViewHolder(var binding: ListItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val clickedPostItem = postItemList.get(adapterPosition)
                postItemTitleClickListener?.invoke(clickedPostItem)
            }
        }

    }
}