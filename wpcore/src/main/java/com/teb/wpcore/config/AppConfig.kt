package com.teb.wpcore.config

class AppConfig {

    companion object{
        const val ENDPOINT = "https://minimalistbaker.com/"
        const val LOGO_URL = "https://minimalistbaker.com/wp-content/themes/mb-2020/assets/images/logo.png"
        const val HIDE_POSTS_FIRST_IMG: Boolean  = true

        val WEB_URL_FRAGMENT_CUSTOM_JS =
            listOf("document.getElementsByTagName('header')[0].style.display = 'none'; ",
                "document.getElementsByClassName('top-bar')[0].style.display='none';")

        var commentStyle = CommentStyle.AHMET

        var NAV_VIEW_LINKS= listOf(
            NavLink(title = "Home", data = null, actionType = NavLinkActionType.ReturnToHome ),
            NavLink(title = "Categories", data = null, actionType = NavLinkActionType.ShowCategories ),
            NavLink(title = "About", data = "5", actionType = NavLinkActionType.OpenPageDetailInFragment ),
            NavLink(title = "Shop", data = "https://minimalistbaker.com/shop/", actionType = NavLinkActionType.ShowInWebViewInFragment ),
            NavLink(title = "Our Baking Mixes", data = "https://shop.minimalistbaker.com/", actionType = NavLinkActionType.OpenInWebBrowser ),
            NavLink(title = "All Recipes", data = "https://minimalistbaker.com/recipe-index/", actionType = NavLinkActionType.ShowInWebViewInFragment ),
            NavLink(title = "Vegan", data = "https://minimalistbaker.com/recipe-index/?fwp_special-diet=vegan", actionType = NavLinkActionType.ShowInWebViewInFragment ),
            NavLink(title = "Gluten-Free", data = "https://minimalistbaker.com/recipe-index/?fwp_special-diet=gluten-free", actionType = NavLinkActionType.ShowInWebViewInFragment ),
        )


    }
}
