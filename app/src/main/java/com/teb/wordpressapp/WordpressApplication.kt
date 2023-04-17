package com.teb.wordpressapp

import android.app.Application
import com.pinchofyum.app.R
import com.teb.wpcore.config.NavLink
import com.teb.wpcore.config.NavLinkActionType
import com.teb.wpcore.config.WordpressConfig

class WordpressApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        WordpressConfig.INSTANCE = WordpressConfig(
            APP_NAME = getString(R.string.app_name),
            ENDPOINT = "https://pinchofyum.com/",
            LOGO_URL = "https://pinchofyum.com/wp-content/uploads/cropped-Pinch-of-Yum-Favicon-512.png",
            HIDE_POSTS_FIRST_IMG = true,
            WEB_URL_FRAGMENT_CUSTOM_JS =
            listOf("document.getElementsByTagName('header')[0].style.display = 'none'; ",
                "document.getElementsByClassName('top-bar')[0].style.display='none';"),
            NAV_VIEW_LINKS = listOf(
                NavLink(title = "Home", data = null, actionType = NavLinkActionType.ReturnToHome),
                NavLink(title = "Categories",
                    data = null,
                    actionType = NavLinkActionType.ShowCategories),
                NavLink(title = "About",
                    data = "https://pinchofyum.com/about",
                    actionType = NavLinkActionType.ShowInWebViewInFragment),
                NavLink(title = "Recipes",
                    data = "https://pinchofyum.com/recipes",
                    actionType = NavLinkActionType.ShowInWebViewInFragment),
                NavLink(title = "Start Here",
                    data = "https://pinchofyum.com/start-here",
                    actionType = NavLinkActionType.ShowInWebViewInFragment)
            )
        )

    }
}