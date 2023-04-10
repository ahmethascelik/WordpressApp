package com.teb.wordpressapp.config


enum class CommentStyle{
    AHMET,
}


data class NavLink(
    val title : String,
    val data : String?,
    val actionType: NavLinkActionType
    )
enum class NavLinkActionType{
     ReturnToHome, ShowInWebView, OpenInWebBrowser, OpenPageDetail
}