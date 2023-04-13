package com.teb.wpcore.ui.screen.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.databinding.FragmentWebUrlBinding
import com.teb.wpcore.ui.BaseFragment
import com.teb.wpcore.ui.screen.postdetail.PostDetailActivity
import com.teb.wpcore.ui.widget.WordpressWebView

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

    val service = ServiceLocator.providePostService()

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

        defaultLoadingCallback = { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.webView.additionalWebViewOnPageFinishRunnable = object : WordpressWebView.AdditionalWebViewOnPageFinishRunnable{
            override fun onPageFinished(view: WebView?, url: String?) {


                binding.progressBar.visibility = View.GONE
                binding.webView.visibility = View.VISIBLE

            }

        }


        binding.webView.onLinkClickListener = { url, slug, webview ->
            service.getPostsOfSlug(slug).makeCall { list->

                if(list != null && list.isNotEmpty()){
                    val postItem = list[0]
                    val i = Intent(activity, PostDetailActivity::class.java)
                    i.putExtra(PostDetailActivity.EXTRA_POST_ID, postItem.id)
                    startActivity(i)
                }else{
                    binding.webView.loadUrl(url)
                }


            }

        }


        urlToOpen?.let {
            binding.webView.loadUrl(it)
            binding.progressBar.visibility = View.VISIBLE
            binding.webView.visibility = View.GONE
        }


    }

}