package com.teb.wpcore.ui.screen.pagedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.model.PostDetail
import com.teb.wpcore.databinding.FragmentPageDetailBinding
import com.teb.wpcore.ui.BaseFragment
import com.teb.wpcore.ui.screen.pagedetail.mvp.PageDetailPresenter
import com.teb.wpcore.ui.screen.pagedetail.mvp.PageDetailView

class PageDetailFragment : BaseFragment(), PageDetailView {

    val presenter = PageDetailPresenter(this)

    private lateinit var pageDetail: PostDetail

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

        presenter.defaultLoadingCallback = { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun makeInitialRequests() {
        presenter.getPageWithId(pageId)
    }

    override fun loadHtml(content: String) {
        binding.webView.loadHtmlContent(content)
    }

}