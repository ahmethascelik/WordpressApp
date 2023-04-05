package com.teb.wordpressapp.ui

import android.R.attr.data
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import com.squareup.picasso.Picasso
import com.teb.wordpressapp.R
import com.teb.wordpressapp.data.ServiceLocator


class PostDetailActivity : BaseActivity() {

    companion object{
        const val EXTRA_POST_ID: String = "EXTRA_POST_ID"
    }

    lateinit var webView: WebView
    val service = ServiceLocator.providePostService()

    lateinit var postId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_post_detail)

        postId = intent.getStringExtra(EXTRA_POST_ID)!!

        initViews();
        makeInitialRequest()
    }



    private fun initViews() {
        val progressBar : ProgressBar = findViewById(R.id.progressBar)
        defaultLoadingCallback = { isLoading ->
            if(isLoading){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        }

         webView = findViewById(R.id.webView)
    }

    private fun makeInitialRequest() {

        val imageView = findViewById<ImageView>(R.id.headerImage)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)


        service.getPostWithId(postId).makeCall { postDetail->

            toolbar.setTitle(postDetail.title())
            Picasso.get().load(postDetail.imageUrl()).into(imageView)


            val content = postDetail.content()

            val htmlContent = content;//"<html><body>" + postDetail.content()+ "</body></html>"

            webView.setWebViewClient(WebViewClient())
            webView.setInitialScale(1);
            webView.getSettings().setJavaScriptEnabled(true)
            webView.getSettings().setDefaultFontSize(12)
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)
            webView.getSettings().setPluginState(WebSettings.PluginState.ON)
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false)

            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

            webView.settings.setGeolocationEnabled(false);

            webView.setWebChromeClient(WebChromeClient())
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

        }

    }
}