package com.teb.wordpressapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wordpressapp.R
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostDetail
import com.teb.wordpressapp.databinding.ActivityCommentBinding
import com.teb.wordpressapp.databinding.ActivityPostDetailBinding

class CommentActivity: BaseActivity() {

//    companion object {
//        const val EXTRA_POST_ID: String = "EXTRA_POST_ID"
//    }

    companion object {
        const val EXTRA_COMMENT_ID: String = "EXTRA_COMMENT_ID"
    }
    //private var postDetail: PostDetail? = null
    private lateinit var binding: ActivityCommentBinding
    val service = ServiceLocator.providePostService()
    lateinit var postId: String
    val adapter = CommentItemsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //postId = intent.getStringExtra(PostDetailActivity.EXTRA_POST_ID)!!
        postId = intent.getStringExtra(CommentActivity.EXTRA_COMMENT_ID)!!

        initViews()
        makeRequest()
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

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

    private fun makeRequest() {
        service.getCommentsWithPostId(postId).makeCall { commentList ->
            adapter.setDataList(commentList!!)
        }
    }
}