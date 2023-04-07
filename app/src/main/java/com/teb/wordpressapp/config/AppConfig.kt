package com.teb.wordpressapp.config

class AppConfig {

    companion object{
        const val ENDPOINT = "https://minimalistbaker.com/"
        const val HTML_HEADER = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "\n" +
                "<style>\n" +
                "    .wp-block-image img{\n" +
                "        width: 200px !important;\n" +
                "        height: 200px !important;\n" +
                "    }\n" +
                "\n" +
                "    iframe{\n" +
                "        width: 200px !important;\n" +
                "        height: 200px !important;\n" +
                "    }\n" +
                "</style>"

        const val STATUS_BAR_COLOR = "#FFDED6"
        const val STATUS_BAR_BLACK_TEXT: Boolean = true
        var commentStyle = CommentStyle.AHMET

        var NAV_VIEW_LINKS= listOf(
            NavLink(title = "Home", data = null, actionType = NavLinkActionType.ReturnToHome ),
            NavLink(title = "About", data = "5", actionType = NavLinkActionType.OpenPageDetail ),
            NavLink(title = "Shop", data = "https://minimalistbaker.com/shop/", actionType = NavLinkActionType.ShowInWebView ),
            NavLink(title = "Our Baking Mixes", data = "https://shop.minimalistbaker.com/", actionType = NavLinkActionType.OpenInWebBrowser ),
            NavLink(title = "All Recipes", data = "https://minimalistbaker.com/recipe-index/", actionType = NavLinkActionType.ShowInWebView ),
            NavLink(title = "Vegan", data = "https://minimalistbaker.com/recipe-index/?fwp_special-diet=vegan", actionType = NavLinkActionType.ShowInWebView ),
            NavLink(title = "Gluten-Free", data = "https://minimalistbaker.com/recipe-index/?fwp_special-diet=gluten-free", actionType = NavLinkActionType.ShowInWebView ),
        )


    }
}
