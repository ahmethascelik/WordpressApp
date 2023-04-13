package com.teb.wordpressapp.ui.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.ui.util.Constants
typealias OnLinkClickListener = (url : String, slug : String, webView : WebView) -> Unit

class WordpressWebView : WebView {

    private var hideFirstImage: Boolean = false
    var onLinkClickListener : OnLinkClickListener? = null

    var additionalWebViewOnPageFinishRunnable : AdditionalWebViewOnPageFinishRunnable? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }


    private fun init() {
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

        this.setOnKeyListener(object : OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN
                    && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (canGoBack()) {
                        goBack()
                        return true
                    }
                }
                return false
            }
        })

        this.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                if (hideFirstImage) {
                    view?.loadUrl("javascript:(function() { document.getElementsByTagName('img')[0].style.display = 'none'; })()");
                }

                AppConfig.WEB_URL_FRAGMENT_CUSTOM_JS.forEach { code ->
                    view?.loadUrl("javascript:(function() { "+code+"})()");
                }

                additionalWebViewOnPageFinishRunnable?.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {

                val url = request?.url?.toString()

                if (url != null && url.startsWith(AppConfig.ENDPOINT)) {

                    val slug = url.replace(AppConfig.ENDPOINT, "").replace("/", "")

                    if (onLinkClickListener != null) {
                        onLinkClickListener?.invoke(url, slug, this@WordpressWebView)
                        return true
                    }else{
                        return super.shouldOverrideUrlLoading(view, request)
                    }


                }else{

                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(browserIntent)
                    return true
                }


            }
        }

    }


    fun loadHtmlContent(content: String, hideFirstImage: Boolean = false) {

        this.hideFirstImage = hideFirstImage

        val htmlContent = Constants.HTML_HEADER + content

        this.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)

    }

    interface AdditionalWebViewOnPageFinishRunnable{
        fun onPageFinished(view: WebView?, url: String?)
    }
}
