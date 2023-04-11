package com.teb.wordpressapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.teb.wordpressapp.R
import com.teb.wordpressapp.databinding.ActivityCommentBinding

class CommentActivity: BaseActivity() {

    private lateinit var binding: ActivityCommentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews();
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
}