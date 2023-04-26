package com.teb.wpcore.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.model.Comment
import com.teb.wpcore.databinding.ActivityCommentsBinding
import com.teb.wpcore.ui.base.BaseActivity
import com.teb.wpcore.ui.mvp.CommentsPresenter
import com.teb.wpcore.ui.mvp.CommentsView

class CommentsActivity: BaseActivity(), CommentsView {

    val commentsPresenter = CommentsPresenter(this)

    companion object {
        const val EXTRA_COMMENT_ID: String = "EXTRA_COMMENT_ID"
    }

    private lateinit var binding: ActivityCommentsBinding
    val adapter = CommentItemsAdapter()
    lateinit var postId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getStringExtra(EXTRA_COMMENT_ID)!!
        initViews()
        makeRequest()
    }

    private fun initViews() {
        binding.toolbar.setTitleTextColor(Color.WHITE)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun makeRequest() {
        commentsPresenter.getCommentsWithPostId(postId)
    }

    override fun fillCommentList(commentList: List<Comment>?) {
        adapter.setDataList(commentList!!)
    }

    override fun showDefaultLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideDefaultLoading() {
        binding.progressBar.visibility = View.GONE
    }
}