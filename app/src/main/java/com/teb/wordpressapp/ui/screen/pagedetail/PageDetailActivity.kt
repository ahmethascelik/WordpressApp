package com.teb.wordpressapp.ui.screen.pagedetail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostDetail
import com.teb.wordpressapp.databinding.ActivityPostDetailBinding
import com.teb.wordpressapp.ui.BaseActivity
import com.teb.wordpressapp.ui.util.loadUrl


class PageDetailActivity : BaseActivity() {

    companion object {
        const val EXTRA_PAGE_ID: String = "EXTRA_PAGE_ID"
    }

    private var postDetail: PostDetail? = null

    private lateinit var binding: ActivityPostDetailBinding

    val service = ServiceLocator.providePostService()

    lateinit var pageId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pageId = intent.getStringExtra(EXTRA_PAGE_ID)!!

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


    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun makeInitialRequest() {


        service.getPageWithId(pageId).makeCall { postDetail ->

            this.postDetail = postDetail

            binding.toolbar.title = postDetail.title()
            binding.headerImage.loadUrl(postDetail.imageUrl())

            binding.webView.loadHtmlContent(postDetail.content())

        }


    }
}