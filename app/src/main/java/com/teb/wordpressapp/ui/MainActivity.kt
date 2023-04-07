package com.teb.wordpressapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.config.NavLink
import com.teb.wordpressapp.config.NavLinkActionType
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



        setupMenu()



    }

    private fun setupMenu() {

        val dataMap = HashMap<String, NavLink>()

        for (navViewLink in AppConfig.NAV_VIEW_LINKS) {
            binding.navView.menu.add(navViewLink.title)

            dataMap.put(navViewLink.title, navViewLink)

        }


        binding.navView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected

            binding.navView.menu.children.forEach { m ->
                m.isChecked = false
            }

            menuItem.isChecked = true
            binding.drawerLayout.close()

            val navViewLink = dataMap.get(menuItem.title)

            navViewLink?.let { navLink ->

                when(navLink.actionType){
                    NavLinkActionType.ReturnToHome -> {

                    }
                    NavLinkActionType.OpenInWebBrowser -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(navLink.link))
                            startActivity(browserIntent)
                        }, 500)
                    }
                }

            }



            true
        }

    }


    private fun makeInitialRequests() {

        service.getPosts().makeCall { result: List<PostItem>? ->
            adapter.setDataList(result!!)
        }

    }



}
