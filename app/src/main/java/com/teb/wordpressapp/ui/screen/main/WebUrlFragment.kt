package com.teb.wordpressapp.ui.screen.main

import android.os.Bundle
import android.view.KeyEvent
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
        savedInstanceState: Bundle?,
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


                binding.progressBar.visibility = View.GONE
                binding.webView.visibility = View.VISIBLE

            }
        }

        binding.webView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN
                    && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                        return true
                    }
                }
                return false
            }
        })



        urlToOpen?.let {
            binding.webView.loadUrl(it)
            binding.progressBar.visibility = View.VISIBLE
            binding.webView.visibility = View.GONE
        }


    }

}