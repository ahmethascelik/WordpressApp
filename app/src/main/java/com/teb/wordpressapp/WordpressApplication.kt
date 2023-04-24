package com.teb.wordpressapp

import android.app.Application
import com.minimalistbaker.app.R
import com.teb.wpcore.config.NavLink
import com.teb.wpcore.config.NavLinkActionType
import com.teb.wpcore.config.WordpressConfig

class WordpressApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        WordpressConfig.INSTANCE = WordpressConfig(
            APP_NAME = getString(R.string.app_name),
            ENDPOINT = "https://joythebaker.com/",
            LOGO_URL = "https://joythebaker.com/wp-content/uploads/2022/08/cropped-JoytheBaker-logo.png",
            HIDE_POSTS_FIRST_IMG = true,
            MAIN_ADD_UNIT_ID = "ca-app-pub-9298171139661017/6450551567",
            WEB_URL_FRAGMENT_CUSTOM_JS =
            listOf("document.getElementsByTagName('header')[0].style.display = 'none'; ",
                "document.getElementsByClassName('top-bar')[0].style.display='none';"),
            NAV_VIEW_LINKS = listOf(
                NavLink(title = "Home", data = null, actionType = NavLinkActionType.ReturnToHome),
                NavLink(title = "Categories",
                    data = null,
                    actionType = NavLinkActionType.ShowCategories),
//                NavLink(title = "About",
//                    data = "5",
//                    actionType = NavLinkActionType.OpenPageDetailInFragment),
                NavLink(title = "Shop",
                    data = "https://shop.joythebaker.com/",
                    actionType = NavLinkActionType.ShowInWebViewInFragment),
//                NavLink(title = "Our Baking Mixes",
//                    data = "https://shop.minimalistbaker.com/",
//                    actionType = NavLinkActionType.OpenInWebBrowser),
                NavLink(title = "All Recipes",
                    data = "https://joythebaker.com/category/baking-recipes/",
                    actionType = NavLinkActionType.ShowInWebViewInFragment),
                NavLink(title = "Vegan",
                    data = "https://joythebaker.com/category/vegan-baking-recipes/",
                    actionType = NavLinkActionType.ShowInWebViewInFragment),
                NavLink(title = "Gluten-Free",
                    data = "https://joythebaker.com/category/gluten-free-baking-recipes/",
                    actionType = NavLinkActionType.ShowInWebViewInFragment),
            )
        )

    }
}