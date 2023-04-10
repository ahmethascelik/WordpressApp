package com.teb.wordpressapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.teb.wordpressapp.R
import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostDetail
import com.teb.wordpressapp.databinding.ActivityPostDetailBinding
import com.teb.wordpressapp.ui.util.loadUrl


class PostDetailActivity : BaseActivity() {

    companion object {
        const val EXTRA_POST_ID: String = "EXTRA_POST_ID"
    }

    private var postDetail: PostDetail? = null

    private lateinit var binding: ActivityPostDetailBinding

    val service = ServiceLocator.providePostService()

    lateinit var postId: String

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
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        binding.toolbar.setTitleTextColor(Color.WHITE)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.toolbar.menu.add(getString(R.string.menu_item_comments))
        binding.toolbar.menu.add(getString(R.string.menu_item_share))

        binding.toolbar.setOnMenuItemClickListener { clickedMenuItem ->
            if (getString(R.string.menu_item_comments).equals(clickedMenuItem.title)) {
                val intent = Intent(this@PostDetailActivity, CommentsActivity::class.java)
                intent.putExtra(CommentsActivity.EXTRA_COMMENT_ID, postId)
                startActivity(intent)
            } else if (getString(R.string.menu_item_share).equals(clickedMenuItem.title)) {
                val intent= Intent()
                intent.action=Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT,"${postDetail?.link}")
                intent.type="text/plain"
                startActivity(Intent.createChooser(intent,"Share To:"))
            }
            true
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun makeInitialRequest() {


        service.getPostWithId(postId).makeCall { postDetail ->

            this.postDetail = postDetail

            binding.toolbar.title = postDetail.title()
            binding.headerImage.loadUrl(postDetail.imageUrl())


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