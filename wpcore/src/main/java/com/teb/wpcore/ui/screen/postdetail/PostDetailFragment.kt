package com.teb.wpcore.ui.screen.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.model.PostDetail
import com.teb.wpcore.databinding.FragmentPostDetailBinding
import com.teb.wpcore.ui.BaseFragment
import com.teb.wpcore.ui.screen.postdetail.mvp.PostDetailFragmentPresenter
import com.teb.wpcore.ui.screen.postdetail.mvp.PostDetailFragmentView

class PostDetailFragment : BaseFragment(), PostDetailFragmentView {


    val presenter = PostDetailFragmentPresenter(view = this)


    companion object{
        private const val EXTRA_POST_ID = "EXTRA_POST_ID"

        fun newInstance(postId: String): PostDetailFragment {
            val args = Bundle()
            args.putString(EXTRA_POST_ID, postId)
            val fragment = PostDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var postId : String

    private lateinit var binding: FragmentPostDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPostDetailBinding.inflate(layoutInflater, container, false)

        arguments?.let {
            postId = it.getString(EXTRA_POST_ID)!!
        }

        makeInitialRequests()
        return binding.root
    }


    private fun makeInitialRequests() {
        presenter.getPostWithId(postId)

    }

    override fun loadHtmlContent(htmlContent: String) {
        binding.webView.loadHtmlContent(htmlContent)
    }


    override fun showDefaultLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideDefaultLoading() {
        binding.progressBar.visibility = View.GONE
    }
}