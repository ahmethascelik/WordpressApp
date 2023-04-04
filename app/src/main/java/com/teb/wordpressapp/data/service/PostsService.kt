package com.teb.wordpressapp.data.service

import com.teb.wordpressapp.data.model.PostItem
import retrofit2.http.GET
import retrofit2.Call

interface PostsService {

    @GET("wp-json/wp/v2/posts/")
    fun getPosts(): Call<List<PostItem>?>?
}