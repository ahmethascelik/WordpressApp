package com.teb.wpcore.config

class WordpressConfig(
    val ENDPOINT: String,
    val LOGO_URL: String,
    val HIDE_POSTS_FIRST_IMG : Boolean,
    val WEB_URL_FRAGMENT_CUSTOM_JS: List<String>,
    var NAV_VIEW_LINKS: List<NavLink>
) {

    companion object{
        var INSTANCE : WordpressConfig? = null
    }
}
