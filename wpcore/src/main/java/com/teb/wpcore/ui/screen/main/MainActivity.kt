package com.teb.wpcore.ui.screen.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.teb.wpcore.R
import com.teb.wpcore.config.WordpressConfig
import com.teb.wpcore.config.NavLink
import com.teb.wpcore.config.NavLinkActionType
import com.teb.wpcore.data.model.Category
import com.teb.wpcore.databinding.ActivityMainBinding
import com.teb.wpcore.ui.BaseActivity
import com.teb.wpcore.ui.screen.main.categories.CategoriesFragment
import com.teb.wpcore.ui.screen.main.categories.CategoryListFragmentActionListenerActivity
import com.teb.wpcore.ui.screen.main.postitem.PostItemListFragment
import com.teb.wpcore.ui.screen.pagedetail.PageDetailActivity
import com.teb.wpcore.ui.screen.pagedetail.PageDetailFragment
import com.teb.wpcore.ui.util.loadUrl


class MainActivity : BaseActivity() , CategoryListFragmentActionListenerActivity {

    lateinit var searchMenuItem: MenuItem
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {

        binding.headerImage.loadUrl(WordpressConfig.INSTANCE!!.LOGO_URL)

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
        setupMenu()

        val fragment = PostItemListFragment.newInstance()
        replaceFragment(fragment)

        binding.toolbar.inflateMenu(R.menu.main_menu)
        searchMenuItem = binding.toolbar.menu.findItem(R.id.action_search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {



            override fun onQueryTextChange(s: String?): Boolean {
                binding.headerImage.visibility = View.GONE

                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                if(currentFragment is SearchableFragment){
                    (currentFragment as SearchableFragment).onSearchQuerySubmitted(query)
                }

                searchMenuItem.collapseActionView()

                binding.headerImage.visibility = View.VISIBLE


                return false
            }
        })

    }



    var currentFragment : Fragment? = null
    private fun replaceFragment(fragment: Fragment,
                                addToBackStackTag : String? = null) {
        currentFragment = fragment
        val fragmentTransaction =  supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, null) ;

        if(addToBackStackTag != null) {
            fragmentTransaction.addToBackStack(addToBackStackTag)
        }else{
            supportFragmentManager.popBackStack()
        }

        fragmentTransaction.commit()
    }

    private fun setupMenu() {

        val dataMap = HashMap<String, NavLink>()

        for (navViewLink in WordpressConfig.INSTANCE!!.NAV_VIEW_LINKS) {
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

                searchMenuItem.isVisible = navLink.actionType == NavLinkActionType.ReturnToHome

                when(navLink.actionType){
                    NavLinkActionType.ReturnToHome -> {

                        val fragment = PostItemListFragment.newInstance()
                        replaceFragment(fragment)

                    }
                    NavLinkActionType.ShowCategories -> {
                        val fragment = CategoriesFragment.newInstance()
                        replaceFragment(fragment)
                    }
                    NavLinkActionType.ShowInWebViewInFragment -> {

                        val fragment = WebUrlFragment.newInstance(navViewLink.data!!)
                        replaceFragment(fragment)

                    }
                    NavLinkActionType.OpenInWebBrowser -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(navLink.data))
                            startActivity(browserIntent)
                        }, 500)
                    }
                    NavLinkActionType.OpenPageDetailInNewActivity -> {
                        val i = Intent(this@MainActivity, PageDetailActivity::class.java)
                        i.putExtra(PageDetailActivity.EXTRA_PAGE_ID, navLink.data)
                        startActivity(i)
                    }
                    NavLinkActionType.OpenPageDetailInFragment -> {
                        val fragment = PageDetailFragment.newInstance(navViewLink.data!!)
                        replaceFragment(fragment)
                    }
                }

            }
            true
        }
    }

    override fun onCategorySelected(category: Category) {

        val fragment = PostItemListFragment.newInstance(category)
        replaceFragment(fragment, "category")
    }


}