package com.teb.wordpressapp.ui

import android.R.attr.data
import android.annotation.SuppressLint
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
import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.databinding.ActivityPostDetailBinding


class PostDetailActivity : BaseActivity() {

    companion object{
        const val EXTRA_POST_ID: String = "EXTRA_POST_ID"
    }

    private lateinit var binding: ActivityPostDetailBinding

    val service = ServiceLocator.providePostService()

    lateinit var postId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getStringExtra(EXTRA_POST_ID)!!

        initViews();
        makeInitialRequest()
    }



    private fun initViews() {

        defaultLoadingCallback = { isLoading ->
            if(isLoading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun makeInitialRequest() {


        service.getPostWithId(postId).makeCall { postDetail->

            binding.toolbar.title = postDetail.title()
            Picasso.get().load(postDetail.imageUrl()).into(binding.headerImage)


            val content = postDetail.content()

            val htmlContent = AppConfig.HTML_HEADER + content

            binding.webView.webViewClient = WebViewClient()
            binding.webView.setInitialScale(1);
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.settings.defaultFontSize = 12
            binding.webView.settings.loadWithOverviewMode = true;
            binding.webView.settings.useWideViewPort = true;
            binding.webView.settings.javaScriptCanOpenWindowsAutomatically = true
            binding.webView.settings.mediaPlaybackRequiresUserGesture = false

            binding.webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE;

            binding.webView.settings.setGeolocationEnabled(false);

            binding.webView.webChromeClient = WebChromeClient()
            binding.webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

        }

    }
}