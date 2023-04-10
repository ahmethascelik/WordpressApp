package com.teb.wordpressapp.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.teb.wordpressapp.R
import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostContent
import com.teb.wordpressapp.data.model.PostDetail
import com.teb.wordpressapp.databinding.ActivityPostDetailBinding
import com.teb.wordpressapp.ui.util.Constants
import com.teb.wordpressapp.ui.util.loadHtmlContent
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
                //comments clicked
                Toast.makeText(this@PostDetailActivity, "Comments Clicked", Toast.LENGTH_LONG).show()

                service.getCommentsWithPostId(postId).makeCall { commentList ->
                    Toast.makeText(this@PostDetailActivity, "Comments Size: "+ commentList?.size , Toast.LENGTH_LONG).show()



                }


            } else if (getString(R.string.menu_item_share).equals(clickedMenuItem.title)) {
                //share clicked
                Toast.makeText(this@PostDetailActivity, "Share Clicked: " + postDetail?.link, Toast.LENGTH_LONG).show()

                //postDetail?.link

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
            binding.webView.loadHtmlContent(postDetail.content())
        }


    }



}