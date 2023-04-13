package com.teb.wpcore.data

import com.teb.wordpressapp.BuildConfig
import com.teb.wpcore.config.AppConfig
import com.teb.wpcore.data.service.PostsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceLocator {

    companion object {

        fun providePostService(): PostsService {

            val okHttpClient = OkHttpClient.Builder()



            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                okHttpClient.addInterceptor(httpLoggingInterceptor)
            }


            val retrofit = Retrofit.Builder()
                .baseUrl(AppConfig.ENDPOINT).client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()



            return retrofit.create(PostsService::class.java)

        }
    }
}