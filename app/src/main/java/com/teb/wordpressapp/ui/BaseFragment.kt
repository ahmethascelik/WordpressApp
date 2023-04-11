package com.teb.wordpressapp.ui

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.gson.JsonSyntaxException
import com.teb.wordpressapp.R
import com.teb.wordpressapp.ui.util.ConnectionUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

typealias PaginationCallback = (header_wp_totalpages : Int) -> Unit


open class BaseFragment : Fragment() {

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

            val connectionUtil = ConnectionUtil(requireActivity())


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

                if (!connectionUtil.isNetworkConnected()) {
                    showAlertDialog(getString(R.string.network_fail_check_connection), tryAgainCallback)

                }else if(t is JsonSyntaxException){
                    showAlertDialog(getString(R.string.network_fail_something_bad_happened), tryAgainCallback)
                }
                else {
                    showAlertDialog(getString(R.string.network_fail_try_again_later),
                        tryAgainCallback)

                }


            }

        })
    }

    private fun showAlertDialog(message: String, tryAgainCallback: TryAgainCallback? = null) {
        val builder = AlertDialog.Builder(this.activity)
        builder.setTitle(getString(R.string.app_name))
        builder.setMessage(message)

        builder.setPositiveButton(android.R.string.ok) { dialog, which ->

        }
        if (tryAgainCallback != null) {
            builder.setNegativeButton("Tekrar Dene") { dialog, which ->
                tryAgainCallback()
            }
        }

        builder.show()
    }

}