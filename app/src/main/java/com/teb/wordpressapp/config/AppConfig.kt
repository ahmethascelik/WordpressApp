package com.teb.wordpressapp.config

class AppConfig {

    companion object{
        const val ENDPOINT = "https://minimalistbaker.com/"
        const val STATUS_BAR_COLOR = "#FFDED6"
        const val STATUS_BAR_BLACK_TEXT: Boolean = true
        var commentStyle = CommentStyle.AHMET

        var NAV_VIEW_LINKS= listOf(
            NavLink(title = "Home", data = null, actionType = NavLinkActionType.ReturnToHome ),
            NavLink(title = "About", data = "5", actionType = NavLinkActionType.OpenPageDetailInFragment ),
            NavLink(title = "Shop", data = "https://minimalistbaker.com/shop/", actionType = NavLinkActionType.ShowInWebViewInFragment ),
            NavLink(title = "Our Baking Mixes", data = "https://shop.minimalistbaker.com/", actionType = NavLinkActionType.OpenInWebBrowser ),
            NavLink(title = "All Recipes", data = "https://minimalistbaker.com/recipe-index/", actionType = NavLinkActionType.ShowInWebViewInFragment ),
            NavLink(title = "Vegan", data = "https://minimalistbaker.com/recipe-index/?fwp_special-diet=vegan", actionType = NavLinkActionType.ShowInWebViewInFragment ),
            NavLink(title = "Gluten-Free", data = "https://minimalistbaker.com/recipe-index/?fwp_special-diet=gluten-free", actionType = NavLinkActionType.ShowInWebViewInFragment ),
        )


    }
}
