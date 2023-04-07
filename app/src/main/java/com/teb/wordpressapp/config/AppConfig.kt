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
            NavLink(title = "Home", link = null, actionType = NavLinkActionType.ReturnToHome ),
            NavLink(title = "About", link = "https://minimalistbaker.com/about/", actionType = NavLinkActionType.OpenInWebBrowser ),
            NavLink(title = "Shop", link = "https://minimalistbaker.com/about/", actionType = NavLinkActionType.OpenInWebBrowser ),
            NavLink(title = "Our Baking Mixes", link = "https://minimalistbaker.com/about/", actionType = NavLinkActionType.OpenInWebBrowser ),
            NavLink(title = "All Recipes", link = "https://minimalistbaker.com/about/", actionType = NavLinkActionType.OpenInWebBrowser ),
            NavLink(title = "Vegan", link = "https://minimalistbaker.com/about/", actionType = NavLinkActionType.OpenInWebBrowser ),
            NavLink(title = "Gluten-Free", link = "https://minimalistbaker.com/about/", actionType = NavLinkActionType.OpenInWebBrowser ),
        )


    }
}
