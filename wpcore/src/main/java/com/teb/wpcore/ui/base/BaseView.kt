package com.teb.wpcore.ui.base

import retrofit2.Call

interface BaseView {
    fun showDefaultLoading()
    fun hideDefaultLoading()
    fun <T> onServiceFailure(call: Call<T>, t: Throwable, tryAgainCallback: TryAgainCallback?)

}