package com.teb.wordpressapp.data

import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.data.service.PostsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceLocator {

    companion object {

        fun providePostService(): PostsService {

            val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(PostsService::class.java)

        }
    }
}