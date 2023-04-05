package com.teb.wordpressapp.ui.util

import android.content.Context
import android.net.ConnectivityManager


class ConnectionUtil(private val context: Context) {


    fun isNetworkConnected( ): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
}