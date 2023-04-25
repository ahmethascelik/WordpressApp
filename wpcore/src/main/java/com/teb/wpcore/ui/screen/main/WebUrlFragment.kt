package com.teb.wpcore.ui.screen.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.teb.wpcore.data.model.PostItem
import com.teb.wpcore.databinding.FragmentWebUrlBinding
import com.teb.wpcore.ui.BaseFragment
import com.teb.wpcore.ui.screen.main.mvp.WebUrlFragmentPresenter
import com.teb.wpcore.ui.screen.main.mvp.WebUrlFragmentView
import com.teb.wpcore.ui.screen.postdetail.PostDetailActivity
import com.teb.wpcore.ui.widget.WordpressWebView

class WebUrlFragment : BaseFragment(), WebUrlFragmentView {

    val presenter = WebUrlFragmentPresenter(view = this)

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

        presenter.defaultLoadingCallback = { isLoading ->
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

            presenter.getPostsOfSlug(slug, url)



        }


        urlToOpen?.let {
            binding.webView.loadUrl(it)
            binding.progressBar.visibility = View.VISIBLE
            binding.webView.visibility = View.GONE
        }


    }

    override fun openPostDetail(postItem: PostItem) {
        val i = Intent(activity, PostDetailActivity::class.java)
        i.putExtra(PostDetailActivity.EXTRA_POST_ID, postItem.id)
        startActivity(i)
    }

    override fun openWebUrl(url: String) {
        binding.webView.loadUrl(url)
    }

}