package com.teb.wpcore.base

import com.teb.wpcore.data.model.PostItem
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface MockCall<T> : Call<T> {

    override fun clone(): Call<T> {
        TODO("Not yet implemented")
    }

    override fun execute(): Response<T> {
        TODO("Not yet implemented")
    }


    override fun isExecuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}