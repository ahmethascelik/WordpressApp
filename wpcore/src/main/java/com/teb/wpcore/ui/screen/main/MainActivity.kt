package com.teb.wpcore.ui.screen.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.teb.wpcore.BuildConfig
import com.teb.wpcore.R
import com.teb.wpcore.config.NavLink
import com.teb.wpcore.config.NavLinkActionType
import com.teb.wpcore.config.WordpressConfig
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.model.Category
import com.teb.wpcore.data.persitance.Persistance
import com.teb.wpcore.databinding.ActivityMainBinding
import com.teb.wpcore.ui.BaseActivity
import com.teb.wpcore.ui.screen.favorites.FavoritesActivity
import com.teb.wpcore.ui.screen.main.categories.CategoriesFragment
import com.teb.wpcore.ui.screen.main.categories.CategoryListFragmentActionListenerActivity
import com.teb.wpcore.ui.screen.main.postitem.PostItemListFragment
import com.teb.wpcore.ui.screen.main_mvp.MainActivityUnit
import com.teb.wpcore.ui.screen.pagedetail.PageDetailActivity
import com.teb.wpcore.ui.screen.pagedetail.PageDetailFragment
import com.teb.wpcore.ui.screen.postdetail.PostDetailActivity
import com.teb.wpcore.ui.util.loadUrl


class MainActivity : BaseActivity() , CategoryListFragmentActionListenerActivity {

    private var admobView: AdView? = null
    lateinit var searchMenuItem: MenuItem
    private lateinit var binding: ActivityMainBinding

    val mainActivityUnit = MainActivityUnit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        persistance = ServiceLocator.providePersistance(application)

        initViews()
        initAds()

        handleDeeplinks()

        getFcmToken()



    }

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
//                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                Log.d("FCM", "token : $token")


            })
    }

    private fun handleDeeplinks() {

        if(intent.action.equals("android.intent.action.VIEW") && intent.data != null){
            Log.d("ahmet", "deeplink")

            val url =  intent.data.toString()
            val slug = url.replace(WordpressConfig.INSTANCE!!.ENDPOINT, "").replace("/", "")

            val service = ServiceLocator.providePostService()
            defaultLoadingCallback = {}
            service.getPostsOfSlug(slug).makeCall { list->
                if(list != null && list.isNotEmpty()){
                    val postItem = list[0]
                    val i = Intent(this@MainActivity, PostDetailActivity::class.java)
                    i.putExtra(PostDetailActivity.EXTRA_POST_ID, postItem.id)
                    startActivity(i)
                }
            }

        }

    }



    override fun onResume() {
        super.onResume()

        persistance.incrementPageViewCount()

        Log.d("pageView", "pageView: "+ persistance.getPageViewCount())

        if (persistance.getPageViewCount() % 4 == 0) {
            admobView?.visibility = View.VISIBLE
        }else{
            admobView?.visibility = View.GONE
        }

    }

    lateinit var persistance : Persistance

    private fun initAds() {


        if ( WordpressConfig.INSTANCE!!.MAIN_ADD_UNIT_ID != null) {
            MobileAds.initialize(this) {}

            val mAdView = AdView(this)
            mAdView.setAdSize(AdSize.LARGE_BANNER)
            if (BuildConfig.DEBUG) {
                // ad unit id for testing
                mAdView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
            } else {
                mAdView.adUnitId = WordpressConfig.INSTANCE!!.MAIN_ADD_UNIT_ID!!
            }

            val dp = resources.displayMetrics.density.toInt()
            mAdView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            (mAdView.layoutParams as LinearLayout.LayoutParams).topMargin = 32 * dp

            mAdView.visibility = View.GONE

            admobView = mAdView
            binding.linearLayout.addView(mAdView, 0)


            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
        }
    }

    private fun initViews() {

        val customLogo = persistance.getCustomLogo()

        binding.headerImage.loadUrl(customLogo ?: WordpressConfig.INSTANCE!!.LOGO_URL)

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
    private fun replaceFragment(
        fragment: Fragment,
        addToBackStackTag: String? = null,
    ) {
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
                    NavLinkActionType.ShowFavorites -> {
                        val i = Intent(this@MainActivity, FavoritesActivity::class.java)
                        val slug = persistance.getCommaSeperatedSlugsForFavoritePostsList()
                        if (slug.isNullOrBlank()){
                            Toast.makeText(this@MainActivity, "Favori listeniz boş.", Toast.LENGTH_SHORT).show()
                        } else {
                            //i.putExtra(PageDetailActivity.EXTRA_PAGE_ID, navLink.data)
                            startActivity(i)
                        }
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