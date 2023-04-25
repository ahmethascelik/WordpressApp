package com.teb.wpcore.ui

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.gson.JsonSyntaxException
import com.teb.wpcore.R
import com.teb.wpcore.config.WordpressConfig
import com.teb.wpcore.ui.screen.main.categories.mvp.BaseView
import com.teb.wpcore.ui.util.ConnectionUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

typealias PaginationCallback = (header_wp_totalpages : Int) -> Unit


abstract class BaseFragment : Fragment(), BaseView {

    private fun showAlertDialog(message: String, tryAgainCallback: TryAgainCallback? = null) {
        val builder = AlertDialog.Builder(this.activity)
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
        val connectionUtil = ConnectionUtil(requireActivity())

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