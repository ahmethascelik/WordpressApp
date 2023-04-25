package com.teb.wpcore.ui.screen.main.categories.mvp

import com.teb.wpcore.ui.LoadingCallback
import com.teb.wpcore.ui.PaginationCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BasePresenter<V : BaseView>(val view: V) {


    var defaultLoadingCallback: LoadingCallback? = null

    fun <T> Call<T>.makeCall(successCallback: (result: T) -> Unit) {
        makeCall(successCallback = successCallback, paginationCallback = null)
    }


    fun <T> Call<T>.makeCall(successCallback: (result: T) -> Unit, paginationCallback: PaginationCallback? = null) {

        if (defaultLoadingCallback != null) {
            this.makeCall(toggleLoading = defaultLoadingCallback!!,
                successCallback = successCallback,
                responseHeaderCallback = paginationCallback)
        } else {
            throw RuntimeException("defaultLoadingCallback != null")
        }


    }


    fun <T> Call<T>.makeCall(
        toggleLoading: LoadingCallback,
        successCallback: (result: T) -> Unit,
        responseHeaderCallback: PaginationCallback? =null
    ) {

        toggleLoading(true)

        this.enqueue(object : Callback<T> {



            override fun onResponse(
                call: Call<T>,
                response: Response<T>,
            ) {
                toggleLoading(false)

                val list = response.body()
                val header_wp_totalpages = response.headers().get("x-wp-totalpages")

                try {
                    val totalPages = Integer.parseInt(header_wp_totalpages!!)
                    responseHeaderCallback?.invoke(totalPages)

                } catch (e: Exception) {
                }

                list?.let {
                    successCallback(it)
                }


            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                toggleLoading(false)

                t.printStackTrace()

                val tryAgainCallback = null

                view.onServiceFailure(call, t, tryAgainCallback )


            }

        })
    }


}
