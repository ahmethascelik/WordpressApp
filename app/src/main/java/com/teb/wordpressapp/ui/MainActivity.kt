package com.teb.wordpressapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teb.wordpressapp.R
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostItem


class MainActivity : BaseActivity() {

    lateinit var progressBar: ProgressBar

    val service = ServiceLocator.providePostService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        initViews()
        makeInitialRequests()


    }

    val adapter = PostItemsAdapter()

    private fun initViews() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.postItemTitleClickListener = { postItem ->
            val i = Intent(this@MainActivity, PostDetailActivity::class.java)
            i.putExtra(PostDetailActivity.EXTRA_POST_ID, postItem.id)
            startActivity(i)
        }

        progressBar = findViewById(R.id.progressBar)

        defaultLoadingCallback = { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }


    }


    private fun makeInitialRequests() {

        service.getPosts().makeCall { result: List<PostItem>? ->
            adapter.setDataList(result!!)
        }

    }



}
