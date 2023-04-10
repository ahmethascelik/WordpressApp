package com.teb.wordpressapp.ui.screen.main

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.teb.wordpressapp.config.AppConfig
import com.teb.wordpressapp.databinding.FragmentWebUrlBinding

import com.teb.wordpressapp.ui.BaseFragment

class WebUrlFragment : BaseFragment() {

    companion object{
        private val EXTRA_URL: String = "EXTRA_URL"

        fun newInstance(urlToOpen: String): WebUrlFragment {
            val args = Bundle()
            args.putString(EXTRA_URL, urlToOpen)
            val fragment = WebUrlFragment()
            fragment.arguments = args
            return fragment
        }
    }


    var urlToOpen : String? = null

    private lateinit var binding: FragmentWebUrlBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebUrlBinding.inflate(inflater, container, false)

        arguments?.let {
            urlToOpen = it.getString(EXTRA_URL)
        }

        initView()
        return binding.root
    }

    private fun initView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient =  object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {

                AppConfig.WEB_URL_FRAGMENT_CUSTOM_JS.forEach { code ->
                    binding.webView.loadUrl("javascript:(function() { "+code+"})()");
                }

            }
        }
        urlToOpen?.let {
            binding.webView.loadUrl(it)
        }

    }
}