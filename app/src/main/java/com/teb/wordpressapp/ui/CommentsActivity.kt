package com.teb.wordpressapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.databinding.ActivityCommentsBinding

class CommentsActivity: BaseActivity() {

    companion object {
        const val EXTRA_COMMENT_ID: String = "EXTRA_COMMENT_ID"
    }

    private lateinit var binding: ActivityCommentsBinding
    val adapter = CommentItemsAdapter()
    lateinit var postId: String

    val service = ServiceLocator.providePostService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getStringExtra(CommentsActivity.EXTRA_COMMENT_ID)!!
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