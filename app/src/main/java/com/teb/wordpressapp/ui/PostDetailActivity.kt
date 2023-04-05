package com.teb.wordpressapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.teb.wordpressapp.R
import com.teb.wordpressapp.data.ServiceLocator

class PostDetailActivity : BaseActivity() {

    companion object{
        const val EXTRA_POST_ID: String = "EXTRA_POST_ID"
    }

    val service = ServiceLocator.providePostService()

    lateinit var postId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_post_detail)

        postId = intent.getStringExtra(EXTRA_POST_ID)!!

        initViews();
        makeInitialRequest()
    }



    private fun initViews() {
        val progressBar : ProgressBar = findViewById(R.id.progressBar)
        defaultLoadingCallback = { isLoading ->
            if(isLoading){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun makeInitialRequest() {
        service.getPostWithId(postId).makeCall {
            Toast.makeText(this@PostDetailActivity, "Çalışıyor", Toast.LENGTH_LONG).show()
        }

    }
}