package com.teb.wordpressapp.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.teb.wordpressapp.R
import com.teb.wordpressapp.data.model.PostItem
import com.teb.wordpressapp.data.service.PostsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


    }

    override fun onResume() {
        super.onResume()
        makeInitialRequests()

    }

    private fun makeInitialRequests() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://minimalistbaker.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: PostsService = retrofit.create(PostsService::class.java)
        var call = service.getPosts()

        call?.enqueue(object : Callback<List<PostItem>?>{
            override fun onResponse(
                call: Call<List<PostItem>?>,
                response: Response<List<PostItem>?>,
            ) {

                Log.d("ahmet", "success")

            }

            override fun onFailure(call: Call<List<PostItem>?>, t: Throwable) {
                Log.d("ahmet", "fail")

            }

        })


    }


}
