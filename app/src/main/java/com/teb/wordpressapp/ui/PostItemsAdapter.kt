package com.teb.wordpressapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teb.wordpressapp.R
import com.teb.wordpressapp.data.model.PostItem
import com.teb.wordpressapp.data.model.PostItemTitle

typealias OnPostItemClickListener = (postItem : PostItem) -> Unit

class PostItemsAdapter : RecyclerView.Adapter<PostItemsAdapter.PostItemViewHolder>() {

    var postItemList = mutableListOf<PostItem>()

    var postItemTitleClickListener : OnPostItemClickListener? = null

    fun setDataList( dataList: List<PostItem>) {
        postItemList.clear()
        postItemList.addAll(dataList)
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_post, parent, false)
        return PostItemViewHolder(view);
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val postItem = postItemList.get(position)

        holder.txtTitle.setText(postItem.title())
        holder.txtExcerpt.setText(postItem.description())
        Picasso.get().load(postItem.imageUrl()).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return postItemList.size
    }


    inner class PostItemViewHolder : RecyclerView.ViewHolder {

        val txtTitle : TextView
        val txtExcerpt : TextView
        val imageView : ImageView

        constructor(itemView: View) : super(itemView){
            txtTitle = itemView.findViewById(R.id.txtTitle)
            txtExcerpt = itemView.findViewById(R.id.txtExcerpt)
            imageView = itemView.findViewById(R.id.image)

            itemView.setOnClickListener {
                val clickedPostItem = postItemList.get(adapterPosition)
                postItemTitleClickListener?.invoke(clickedPostItem)
            }
        }

    }
}