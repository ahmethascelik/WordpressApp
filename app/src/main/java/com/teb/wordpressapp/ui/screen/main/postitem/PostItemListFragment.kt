package com.teb.wordpressapp.ui.screen.main.postitem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostItem
import com.teb.wordpressapp.databinding.FragmentPostItemListBinding
import com.teb.wordpressapp.ui.BaseFragment
import com.teb.wordpressapp.ui.screen.main.MainActivity
import com.teb.wordpressapp.ui.screen.main.SearchableFragment
import com.teb.wordpressapp.ui.screen.postdetail.PostDetailActivity

class PostItemListFragment : BaseFragment() , SearchableFragment {

    companion object {
        fun newInstance(): PostItemListFragment {
            return PostItemListFragment()
        }
    }

    val service = ServiceLocator.providePostService()


    private lateinit var binding: FragmentPostItemListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostItemListBinding.inflate(layoutInflater, container, false)
        initView()
        makeInitialRequests()

        return binding.root
    }


    val adapter = PostItemsAdapter()

    private fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter


        adapter.postItemTitleClickListener = { postItem ->
            val i = Intent(activity, PostDetailActivity::class.java)
            i.putExtra(PostDetailActivity.EXTRA_POST_ID, postItem.id)
            startActivity(i)
        }

        defaultLoadingCallback = { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.btnClearSearch.setOnClickListener {
            binding.layoutSearchQueryInfo.visibility = View.GONE
            makeInitialRequests()
        }
    }


    private fun makeInitialRequests() {

        service.getPosts().makeCall { result: List<PostItem>? ->
            adapter.setDataList(result!!)
        }
    }

    override fun onSearchQuerySubmitted(query: String) {


        binding.layoutSearchQueryInfo.visibility = View.VISIBLE
        binding.txtSearchQuery.text = "\"$query\" için sonuçlar gösteriliyor"

        service.getPosts(search = query).makeCall { result: List<PostItem>? ->
            adapter.setDataList(result!!)
        }
    }
}