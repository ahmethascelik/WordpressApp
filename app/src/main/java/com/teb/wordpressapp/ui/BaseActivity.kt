package com.teb.wordpressapp.ui

import android.app.Activity
import android.app.AlertDialog
import com.teb.wordpressapp.R
import com.teb.wordpressapp.ui.util.ConnectionUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

typealias LoadingCallback = (isLoading: Boolean) -> Unit

open class BaseActivity : Activity() {

    var defaultLoadingCallback: LoadingCallback? = null


    fun <T> Call<T>.makeCall(successCallback: (result: T) -> Unit) {

        if(defaultLoadingCallback != null){
            this.makeCall(toggleLoading = defaultLoadingCallback!!, successCallback = successCallback)
        }else{
            throw RuntimeException("defaultLoadingCallback != null")
        }


    }


    fun <T> Call<T>.makeCall(
        toggleLoading: LoadingCallback,
        successCallback: (result: T) -> Unit,
    ) {

        toggleLoading(true)

        this.enqueue(object : Callback<T> {

            val connectionUtil = ConnectionUtil(this@BaseActivity)


            override fun onResponse(
                call: Call<T>,
                response: Response<T>,
            ) {
                toggleLoading(false)

                val list = response.body()

                list?.let {
                    successCallback(it)
                }


            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                toggleLoading(false)

                if (!connectionUtil.isNetworkConnected()) {
                    showAlertDialog(getString(R.string.network_fail_check_connection))

                } else {
                    showAlertDialog(getString(R.string.network_fail_try_again_later))

                }


            }

        })
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this@BaseActivity)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage(message)

        builder.setPositiveButton(android.R.string.ok) { dialog, which ->

        }

        builder.show()
    }

}

