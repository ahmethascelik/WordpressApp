package com.teb.wordpressapp.data.service

import com.teb.wordpressapp.data.model.PostDetail
import com.teb.wordpressapp.data.model.PostItem
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

interface PostsService {

    @GET("wp-json/wp/v2/posts/?_fields=id,yoast_head_json.title,yoast_head_json.description,yoast_head_json.og_image")
    fun getPosts(): Call<List<PostItem>?>


    @GET("wp-json/wp/v2/posts/{id}")
    fun getPostWithId(@Path("id") id: String): Call<PostDetail>


}