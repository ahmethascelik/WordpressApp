package com.teb.wordpressapp.ui.screen.main.postitem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.data.model.PostItem
import com.teb.wordpressapp.databinding.FragmentPostItemListBinding
import com.teb.wordpressapp.ui.BaseFragment
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
        getPostWithPageNum(1)

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
            adapter.setDataList(emptyList())
            getPostWithPageNum(1)
            binding.layoutSearchQueryInfo.visibility = View.GONE
        }

        binding.btnPaginationFirst.setOnClickListener {
            gotoPage(1)
        }
        binding.btnPaginationLast.setOnClickListener {
            gotoPage(lastPage)
        }

        binding.btnPaginationNext.setOnClickListener {
            gotoPage(currentPage + 1)
        }
        binding.btnPaginationPrev.setOnClickListener {
            gotoPage(currentPage - 1)
        }
    }

    private fun gotoPage(pageNum: Int) {
        getPostWithPageNum(pageNum)
    }

    var currentPage: Int = 1
    var lastPage: Int = 1

    private fun getPostWithPageNum(currentPage: Int) {

        this.currentPage = currentPage

        binding.paginationLayout.visibility = View.GONE

        service.getPosts(page = ""+currentPage).makeCall(successCallback = { result: List<PostItem>? ->
            adapter.setDataList(result!!)
        }, paginationCallback = { header_wp_totalpages ->

            lastPage = header_wp_totalpages

            binding.paginationLayout.visibility = View.VISIBLE

            binding.txtPaginationInfo.text = "$currentPage/$header_wp_totalpages"

            binding.btnPaginationFirst.isEnabled = 1 != currentPage
            binding.btnPaginationPrev.isEnabled = 1 != currentPage
            binding.btnPaginationLast.isEnabled = currentPage != header_wp_totalpages
            binding.btnPaginationNext.isEnabled = currentPage != header_wp_totalpages

        })
    }

    override fun onSearchQuerySubmitted(query: String) {
        binding.paginationLayout.visibility = View.GONE

        binding.layoutSearchQueryInfo.visibility = View.VISIBLE
        binding.txtSearchQuery.text = "\"$query\" için sonuçlar aranıyor"
        adapter.setDataList(emptyList())

        service.getPosts(search = query).makeCall( successCallback = { result: List<PostItem>? ->
            adapter.setDataList(result!!)

            if (result.isEmpty()) {
                binding.txtSearchQuery.text = "\"$query\" için sonuç bulunamadı!"
            } else {
                binding.txtSearchQuery.text = "\"$query\" için sonuçlar gösteriliyor"
            }
        }, paginationCallback = { header_wp_totalpages ->
            Toast.makeText(activity, ""+ header_wp_totalpages, Toast.LENGTH_SHORT).show()
        })
    }
}