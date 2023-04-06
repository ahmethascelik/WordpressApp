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

    private fun makeInitialRequest() {


        service.getPostWithId(postId).makeCall { postDetail->

            binding.toolbar.setTitle(postDetail.title())
            Picasso.get().load(postDetail.imageUrl()).into(binding.headerImage)


            val content = postDetail.content()

            val htmlContent ="<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "\n" +
                    "<style>\n" +
                    "    .wp-block-image img{\n" +
                    "        width: 200px !important;\n" +
                    "        height: 200px !important;\n" +
                    "    }\n" +
                    "\n" +
                    "    iframe{\n" +
                    "        width: 200px !important;\n" +
                    "        height: 200px !important;\n" +
                    "    }\n" +
                    "</style>" + content;//"<html><body>" + postDetail.content()+ "</body></html>"

            binding.webView.setWebViewClient(WebViewClient())
            binding.webView.setInitialScale(1);
            binding.webView.getSettings().setJavaScriptEnabled(true)
            binding.webView.getSettings().setDefaultFontSize(12)
            binding.webView.getSettings().setLoadWithOverviewMode(true);
            binding.webView.getSettings().setUseWideViewPort(true);
            binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true)
            binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON)
            binding.webView.getSettings().setMediaPlaybackRequiresUserGesture(false)

            binding.webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            binding.webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

            binding.webView.settings.setGeolocationEnabled(false);

            binding.webView.setWebChromeClient(WebChromeClient())
            binding.webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

        }

    }
}