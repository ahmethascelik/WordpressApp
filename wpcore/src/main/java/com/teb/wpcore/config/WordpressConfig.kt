package com.teb.wpcore.config

class WordpressConfig(
    val APP_NAME: String,
    val ENDPOINT: String,
    val LOGO_URL: String,
    val HIDE_POSTS_FIRST_IMG: Boolean,
    val WEB_URL_FRAGMENT_CUSTOM_JS: List<String>,
    var NAV_VIEW_LINKS: List<NavLink>,
    val MAIN_ADD_UNIT_ID: String? = null,
    val ONE_SIGNAL_APP_ID: String? = null,
) {


    companion object {
        var INSTANCE: WordpressConfig? = null
    }
}
