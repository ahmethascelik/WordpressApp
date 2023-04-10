package com.teb.wordpressapp.ui.util

import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

fun WebView.loadHtmlContent(content: String) {

    val htmlContent = Constants.HTML_HEADER + content

    this.webViewClient = WebViewClient()
    this.setInitialScale(1);
    this.settings.javaScriptEnabled = true
    this.settings.defaultFontSize = 12
    this.settings.loadWithOverviewMode = true;
    this.settings.useWideViewPort = true;
    this.settings.javaScriptCanOpenWindowsAutomatically = true
    this.settings.mediaPlaybackRequiresUserGesture = false

    this.settings.cacheMode = WebSettings.LOAD_NO_CACHE;

    this.settings.setGeolocationEnabled(false);

    this.webChromeClient = WebChromeClient()
    this.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

}
