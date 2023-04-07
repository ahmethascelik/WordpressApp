package com.teb.wordpressapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teb.wordpressapp.R
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostItem
import com.teb.wordpressapp.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    val service = ServiceLocator.providePostService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        initViews()
        makeInitialRequests()


    }

    val adapter = PostItemsAdapter()

    private fun initViews() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.postItemTitleClickListener = { postItem ->
            val i = Intent(this@MainActivity, PostDetailActivity::class.java)
            i.putExtra(PostDetailActivity.EXTRA_POST_ID, postItem.id)
            startActivity(i)
        }


        defaultLoadingCallback = { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }


        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            binding.drawerLayout.close()
            true
        }



    }


    private fun makeInitialRequests() {

        service.getPosts().makeCall { result: List<PostItem>? ->
            adapter.setDataList(result!!)
        }

    }



}
