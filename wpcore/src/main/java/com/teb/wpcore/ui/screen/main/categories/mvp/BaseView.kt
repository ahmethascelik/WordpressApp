package com.teb.wpcore.ui.screen.main.categories.mvp

import com.teb.wpcore.ui.TryAgainCallback
import retrofit2.Call

public interface BaseView {
    fun showDefaultLoading()
    fun hideDefaultLoading()
    fun <T> onServiceFailure(call: Call<T>, t: Throwable, tryAgainCallback: TryAgainCallback?)

}