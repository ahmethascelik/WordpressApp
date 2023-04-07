package com.teb.wordpressapp.ui

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wordpressapp.R
import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostItem
import com.teb.wordpressapp.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    val service = ServiceLocator.providePostService()

    lateinit var  mainThreadHandler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        mainThreadHandler = Handler(Looper.getMainLooper())


        initViews()
        makeInitialRequests()




    }
    val titleRenamerUiRunnable = Runnable{
        binding.textView.text = "Ahmet"

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

            binding.navView.menu.children.forEach { m ->
                m.isChecked = false
            }

            menuItem.isChecked = true
            binding.drawerLayout.close()

            if(menuItem.itemId == R.id.item4){

                mainThreadHandler.postDelayed(titleRenamerUiRunnable, 5000)


            }


            if(menuItem.itemId == R.id.item5){

                val runnable : Runnable= Runnable{
                    Thread.sleep(1000)

                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.ENDPOINT))
                    startActivity(browserIntent)
                }


                val browserOpenerThread = Thread (runnable)


                browserOpenerThread.start()


            }



            true
        }


        binding.navView.inflateMenu(R.menu.nav_menu)


    }


    private fun makeInitialRequests() {

        service.getPosts().makeCall { result: List<PostItem>? ->
            adapter.setDataList(result!!)
        }

    }



}
