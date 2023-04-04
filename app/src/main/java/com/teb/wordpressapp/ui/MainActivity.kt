package com.teb.wordpressapp.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teb.wordpressapp.R
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostItem
import com.teb.wordpressapp.data.service.PostsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : Activity() {

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

    }

    private fun makeInitialRequests() {


        service.getPosts().enqueue(object : Callback<List<PostItem>?>{
            override fun onResponse(
                call: Call<List<PostItem>?>,
                response: Response<List<PostItem>?>,
            ) {

                var list = response.body()

                list?.let {
                    adapter.setDataList(it)
                }

                Log.d("ahmet", "success")

            }

            override fun onFailure(call: Call<List<PostItem>?>, t: Throwable) {
                Log.d("ahmet", "fail")

            }

        })


    }


}
