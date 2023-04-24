package com.teb.wpcore.ui.screen.main.favorites

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.databinding.ActivityCommentsBinding
import com.teb.wpcore.databinding.ActivityFavoritesBinding
import com.teb.wpcore.ui.BaseActivity
import com.teb.wpcore.ui.CommentItemsAdapter
import com.teb.wpcore.ui.CommentsActivity

class FavoritesActivity: BaseActivity() {

    companion object {
        const val EXTRA_FAVORIES_SLUG: String = "EXTRA_FAVORIES_SLUG"
    }

    private lateinit var binding: ActivityFavoritesBinding
    val adapter = FavoritiesItemsAdapter()
    lateinit var postId: String

    val service = ServiceLocator.providePostService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postId = intent.getStringExtra(FavoritesActivity.EXTRA_FAVORIES_SLUG)!!
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
        service.getPostsOfSlugsCommaSeperated(postId).makeCall { list->
            Toast.makeText(this@FavoritesActivity, "list"+ list?.size, Toast.LENGTH_SHORT).show()
            if (list != null) {
                adapter.setDataList(list)
            }
        }
    }
}