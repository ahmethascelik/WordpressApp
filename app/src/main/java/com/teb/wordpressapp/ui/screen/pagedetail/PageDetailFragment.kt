package com.teb.wordpressapp.ui.screen.pagedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostDetail
import com.teb.wordpressapp.databinding.FragmentPageDetailBinding
import com.teb.wordpressapp.ui.BaseFragment

class PageDetailFragment : BaseFragment() {

    private lateinit var pageDetail: PostDetail
    val service = ServiceLocator.providePostService()

    companion object{
        private const val EXTRA_PAGE_ID = "EXTRA_PAGE_ID"

        fun newInstance(pageId: String): PageDetailFragment {
            val args = Bundle()
            args.putString(EXTRA_PAGE_ID, pageId)
            val fragment = PageDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var pageId : String

    private lateinit var binding: FragmentPageDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPageDetailBinding.inflate(layoutInflater, container, false)

        arguments?.let {
            pageId = it.getString(EXTRA_PAGE_ID)!!
        }

        initView()
        makeInitialRequests()
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
    }

    private fun makeInitialRequests() {

        service.getPageWithId(pageId).makeCall { pageDetail ->

            this.pageDetail = pageDetail
            binding.webView.loadHtmlContent(pageDetail.content())
        }



    }

}