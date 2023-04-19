package com.teb.wordpressapp

import android.app.Application
import android.util.Log
import com.minimalistbaker.app.R
import com.onesignal.OneSignal
import com.teb.wpcore.config.NavLink
import com.teb.wpcore.config.NavLinkActionType
import com.teb.wpcore.config.WordpressConfig

class WordpressApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        WordpressConfig.INSTANCE = WordpressConfig(
            APP_NAME = getString(R.string.app_name),
            ENDPOINT = "https://minimalistbaker.com/",
            LOGO_URL = "https://minimalistbaker.com/wp-content/themes/mb-2020/assets/images/logo.png",
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
                NavLink(title = "About",
                    data = "5",
                    actionType = NavLinkActionType.OpenPageDetailInFragment),
                NavLink(title = "Shop",
                    data = "https://minimalistbaker.com/shop/",
                    actionType = NavLinkActionType.ShowInWebViewInFragment),
                NavLink(title = "Our Baking Mixes",
                    data = "https://shop.minimalistbaker.com/",
                    actionType = NavLinkActionType.OpenInWebBrowser),
                NavLink(title = "All Recipes",
                    data = "https://minimalistbaker.com/recipe-index/",
                    actionType = NavLinkActionType.ShowInWebViewInFragment),
                NavLink(title = "Vegan",
                    data = "https://minimalistbaker.com/recipe-index/?fwp_special-diet=vegan",
                    actionType = NavLinkActionType.ShowInWebViewInFragment),
                NavLink(title = "Gluten-Free",
                    data = "https://minimalistbaker.com/recipe-index/?fwp_special-diet=gluten-free",
                    actionType = NavLinkActionType.ShowInWebViewInFragment),
            )
        )


        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId("70c77723-cc48-44a2-b35e-ad0c25cc4a72");

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
        OneSignal.promptForPushNotifications();



    }
}