package com.teb.wordpressapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.fragment.app.FragmentActivity
import com.google.gson.JsonSyntaxException
import com.teb.wordpressapp.R
import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.ui.util.ConnectionUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


typealias LoadingCallback = (isLoading: Boolean) -> Unit
typealias TryAgainCallback = () -> Unit

open class BaseActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setStatusBarColor(Color.parseColor(AppConfig.STATUS_BAR_COLOR))
//
//        if (AppConfig.STATUS_BAR_BLACK_TEXT) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }

    }

    private fun setStatusBarColor(@ColorInt color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(color)

    }

    var defaultLoadingCallback: LoadingCallback? = null


    fun <T> Call<T>.makeCall(successCallback: (result: T) -> Unit) {

        if (defaultLoadingCallback != null) {
            this.makeCall(toggleLoading = defaultLoadingCallback!!,
                successCallback = successCallback)
        } else {
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
        val builder = AlertDialog.Builder(this@BaseActivity)
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

