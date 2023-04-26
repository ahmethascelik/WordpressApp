package com.teb.wpcore.ui.screen.favorites

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.persitance.Persistance
import com.teb.wpcore.databinding.ActivityFavoritesBinding
import com.teb.wpcore.ui.base.BaseActivity


class FavoritesActivity: BaseActivity() {

    companion object {
        const val EXTRA_FAVORITES_SLUG: String = "EXTRA_FAVORITES_SLUG"
    }

    private lateinit var binding: ActivityFavoritesBinding
    val adapter = FavoritesItemsAdapter()
    lateinit var favorites: String

    val service = ServiceLocator.providePostService()
    lateinit var persistance : Persistance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        persistance = ServiceLocator.providePersistance(application)
        val slug = persistance.getCommaSeperatedSlugsForFavoritePostsList()

        if (slug != "") {
            favorites = slug
        } else {
            favorites = intent.getStringExtra(EXTRA_FAVORITES_SLUG)!!
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
            adapter.setDataList(list!!)
        }
    }
}