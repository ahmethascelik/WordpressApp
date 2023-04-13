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
import com.teb.wordpressapp.ui.widget.WordpressWebView

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

        binding.webView.additionalWebViewOnPageFinishRunnable = object : WordpressWebView.AdditionalWebViewOnPageFinishRunnable{
            override fun onPageFinished(view: WebView?, url: String?) {


                binding.progressBar.visibility = View.GONE
                binding.webView.visibility = View.VISIBLE

            }

        }


        urlToOpen?.let {
            binding.webView.loadUrl(it)
            binding.progressBar.visibility = View.VISIBLE
            binding.webView.visibility = View.GONE
        }


    }

}