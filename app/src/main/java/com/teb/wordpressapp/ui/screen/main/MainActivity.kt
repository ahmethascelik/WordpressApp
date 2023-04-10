package com.teb.wordpressapp.ui.screen.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.teb.wordpressapp.R
import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.config.NavLink
import com.teb.wordpressapp.config.NavLinkActionType
import com.teb.wordpressapp.databinding.ActivityMainBinding
import com.teb.wordpressapp.ui.BaseActivity
import com.teb.wordpressapp.ui.screen.main.postitem.PostItemListFragment
import com.teb.wordpressapp.ui.screen.pagedetail.PageDetailFragment


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initViews()

    }


    private fun initViews() {



        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }



        setupMenu()


        val fragment = PostItemListFragment.newInstance()

        replaceFragment(fragment)

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, null)
            .commit()
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

                        val fragment = PostItemListFragment.newInstance()
                        replaceFragment(fragment)

                    }
                    NavLinkActionType.ShowInWebView -> {

                        val fragment = WebUrlFragment.newInstance(navViewLink.data!!)
                        replaceFragment(fragment)

                    }
                    NavLinkActionType.OpenInWebBrowser -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(navLink.data))
                            startActivity(browserIntent)
                        }, 500)
                    }
                    NavLinkActionType.OpenPageDetail -> {
//                        val i = Intent(this@MainActivity, PageDetailActivity::class.java)
//                        i.putExtra(PageDetailActivity.EXTRA_PAGE_ID, navLink.data)
//                        startActivity(i)


                        val fragment = PageDetailFragment.newInstance(navViewLink.data!!)
                        replaceFragment(fragment)
                    }
                }

            }



            true
        }

    }



}