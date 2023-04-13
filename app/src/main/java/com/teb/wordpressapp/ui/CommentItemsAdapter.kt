package com.teb.wordpressapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.htmlEncode
import androidx.recyclerview.widget.RecyclerView
import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.data.model.Comment
import com.teb.wordpressapp.databinding.ListItemPostBinding
import com.teb.wordpressapp.ui.util.loadUrl

class CommentItemsAdapter : RecyclerView.Adapter<CommentItemsAdapter.CommentItemViewHolder>() {

    var commentItemList = mutableListOf<Comment>()

    fun setDataList(dataList: List<Comment>) {
        commentItemList.clear()
        commentItemList.addAll(dataList)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
        val binding =
            ListItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CommentItemViewHolder(binding);
    }

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        val commentItem = commentItemList.get(position)

        holder.binding.apply {
            txtTitle.text = commentItem.author_name
            txtExcerpt.text = commentItem.content?.rendered?.htmlEncode()
            image.loadUrl(commentItem.author_avatar_urls?.get("96"))
        }
    }

    override fun getItemCount(): Int {
        return commentItemList.size
    }

    inner class CommentItemViewHolder(var binding: ListItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val clickedPostItem = commentItemList.get(adapterPosition)
                //postItemTitleClickListener?.invoke(clickedPostItem)
            }
        }

    }
}