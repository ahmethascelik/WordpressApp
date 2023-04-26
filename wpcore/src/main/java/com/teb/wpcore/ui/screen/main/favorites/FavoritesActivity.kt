package com.teb.wpcore.ui.screen.main.favorites

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.persitance.Persistance
import com.teb.wpcore.databinding.ActivityCommentsBinding
import com.teb.wpcore.databinding.ActivityFavoritesBinding
import com.teb.wpcore.ui.BaseActivity
import com.teb.wpcore.ui.CommentItemsAdapter
import com.teb.wpcore.ui.CommentsActivity

class FavoritesActivity: BaseActivity() {

    companion object {
        const val EXTRA_FAVORTITES_SLUG: String = "EXTRA_FAVORTITES_SLUG"
    }

    private lateinit var binding: ActivityFavoritesBinding
    val adapter = FavoritiesItemsAdapter()
    lateinit var favoritesSlug: String

    val service = ServiceLocator.providePostService()
    val persistance: Persistance = ServiceLocator.providePersistance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slug = persistance.getCommaSeperatedSlugsForFavoritePostsList(this)

        favoritesSlug = if (slug != "") {
            slug
        } else {
            intent.getStringExtra(FavoritesActivity.EXTRA_FAVORTITES_SLUG) ?: ""
        }

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
        service.getPostsOfSlugsCommaSeperated(favoritesSlug).makeCall { list ->
            adapter.setDataList(list!!)
            Toast.makeText(this, "list: " + list?.size , Toast.LENGTH_SHORT).show()
        }
    }
}