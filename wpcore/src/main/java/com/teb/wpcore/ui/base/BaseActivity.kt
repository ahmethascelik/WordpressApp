package com.teb.wpcore.ui.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.fragment.app.FragmentActivity
import com.google.gson.JsonSyntaxException
import com.teb.wpcore.R
import com.teb.wpcore.config.WordpressConfig
import com.teb.wpcore.ui.util.ConnectionUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


typealias LoadingCallback = (isLoading: Boolean) -> Unit
typealias TryAgainCallback = () -> Unit

abstract class BaseActivity : FragmentActivity(), BaseView {

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



    private fun showAlertDialog(message: String, tryAgainCallback: TryAgainCallback? = null) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(WordpressConfig.INSTANCE!!.APP_NAME)
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


    override fun <T> onServiceFailure(
        call: Call<T>,
        t: Throwable,
        tryAgainCallback: TryAgainCallback?
    ) {
        val connectionUtil = ConnectionUtil(this)

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


}

