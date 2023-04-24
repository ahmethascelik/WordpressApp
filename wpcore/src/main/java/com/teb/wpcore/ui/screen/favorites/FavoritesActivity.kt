package com.teb.wpcore.ui.screen.favorites

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.persitance.Persistance
import com.teb.wpcore.databinding.ActivityFavoritesBinding
import com.teb.wpcore.ui.BaseActivity

class FavoritesActivity: BaseActivity() {

    companion object {
        const val EXTRA_FAVORITES_SLUG: String = "EXTRA_FAVORITES_SLUG"
    }

    private lateinit var binding: ActivityFavoritesBinding
    val adapter = FavoritesItemsAdapter()
    lateinit var favorites: String

    val service = ServiceLocator.providePostService()
    val persistance : Persistance = ServiceLocator.providePersistance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val slug = persistance.getCommaSeperatedSlugsForFavoritePostsList(this)
        Log.d("evren", "evren: "+ persistance.getPageViewCount(this))
        if (slug != "") {
            favorites = slug
        } else {
            favorites = intent.getStringExtra(FavoritesActivity.EXTRA_FAVORITES_SLUG)!!
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
        service.getPostsOfSlugsCommaSeperated(favorites).makeCall { list->
            Toast.makeText(this, "list"+ list?.size, Toast.LENGTH_SHORT).show()
            Log.d("favoriteList: ", "${list}")
            adapter.setDataList(list!!)
        }
    }
}