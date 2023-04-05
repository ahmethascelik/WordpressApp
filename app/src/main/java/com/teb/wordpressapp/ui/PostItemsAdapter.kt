package com.teb.wordpressapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.teb.wordpressapp.R
import com.teb.wordpressapp.data.model.PostItem

class PostItemsAdapter : RecyclerView.Adapter<PostItemsAdapter.PostItemViewHolder>() {

    var postItemList = mutableListOf<PostItem>()

    fun setDataList( dataList: List<PostItem>) {
        postItemList.clear()
        postItemList.addAll(dataList)
        notifyDataSetChanged()

    }


    class PostItemViewHolder : RecyclerView.ViewHolder {

        val textView : TextView

        constructor(itemView: View) : super(itemView){
            textView = itemView.findViewById(R.id.textView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_post, parent, false)
        return PostItemViewHolder(view);
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val postItem = postItemList.get(position)

        holder.textView.setText(postItem.imageUrl())
    }

    override fun getItemCount(): Int {
        return postItemList.size
    }

}