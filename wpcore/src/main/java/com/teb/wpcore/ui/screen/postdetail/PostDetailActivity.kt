package com.teb.wpcore.ui.screen.postdetail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle


import android.view.View
import com.teb.wpcore.R
import com.teb.wpcore.config.WordpressConfig
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.model.PostDetail
import com.teb.wpcore.databinding.ActivityPostDetailBinding
import com.teb.wpcore.ui.BaseActivity
import com.teb.wpcore.ui.CommentsActivity
import com.teb.wpcore.ui.util.loadUrl


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

        binding.webView.onLinkClickListener = { url, slug, webview ->
            service.getPostsOfSlug(slug).makeCall { list->

                if(list != null && list.isNotEmpty()){
                    val postItem = list[0]
                    val i = Intent(this, PostDetailActivity::class.java)
                    i.putExtra(PostDetailActivity.EXTRA_POST_ID, postItem.id)
                    startActivity(i)
                }else{
                    binding.webView.loadUrl(url)
                }


            }

        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun makeInitialRequest() {


        service.getPostWithId(postId).makeCall { postDetail ->

            this.postDetail = postDetail

            binding.toolbar.title = postDetail.title()
//            binding.collapsingToolbarLayout.title = postDetail.title()

            binding.headerImage.loadUrl(postDetail.imageUrl())
            binding.webView.loadHtmlContent(postDetail.content(), WordpressConfig.INSTANCE!!.HIDE_POSTS_FIRST_IMG)
        }


    }



}