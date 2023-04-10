package com.teb.wordpressapp.ui.screen.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostDetail
import com.teb.wordpressapp.databinding.FragmentPostDetailBinding
import com.teb.wordpressapp.ui.BaseFragment
import com.teb.wordpressapp.ui.util.loadHtmlContent

class PostDetailFragment : BaseFragment() {

    private lateinit var postDetail: PostDetail
    val service = ServiceLocator.providePostService()

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

        service.getPostWithId(postId).makeCall { postDetail ->

            this.postDetail = postDetail
            binding.webView.loadHtmlContent(postDetail.content())
        }



    }

}