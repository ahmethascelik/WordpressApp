package com.teb.wpcore.ui.screen.main.postitem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wpcore.data.model.Category
import com.teb.wpcore.data.model.PostItem
import com.teb.wpcore.databinding.FragmentPostItemListBinding
import com.teb.wpcore.ui.BaseFragment
import com.teb.wpcore.ui.screen.main.SearchableFragment
import com.teb.wpcore.ui.screen.main.postitem.mvp.PostItemListPresenter
import com.teb.wpcore.ui.screen.main.postitem.mvp.PostItemListView
import com.teb.wpcore.ui.screen.postdetail.PostDetailActivity
import com.teb.wpcore.ui.widget.OnPageChangeRequestListener

class PostItemListFragment : BaseFragment() , SearchableFragment, PostItemListView {

    val presenter = PostItemListPresenter(view = this)


    companion object {

        const val ARG_CATEGORY  = "ARG_CATEGORY"

        fun newInstance(category: Category? = null): PostItemListFragment{
            val args = Bundle()

            args.putParcelable(ARG_CATEGORY, category)

            val fragment = PostItemListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var category: Category? = null


    private lateinit var binding: FragmentPostItemListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        arguments?.let {  arg->
            category= arg.getParcelable(ARG_CATEGORY)
        }


        binding = FragmentPostItemListBinding.inflate(layoutInflater, container, false)
        initView()
        if (category != null) {
            presenter.getPostOfCategoryWithPageNum(1)
        }else{
            presenter.getPostWithPageNum(1)
        }

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

        binding.btnClearSearch.setOnClickListener {
            adapter.setDataList(emptyList())
            presenter.getPostWithPageNum(1)
            binding.layoutSearchQueryInfo.visibility = View.GONE
        }

        category?.let {
            binding.layoutCategoryInfo.visibility = View.VISIBLE
            binding.txtCategoryTitle.text = it.name
        }

    }

    override fun onSearchQuerySubmitted(query: String) {

        presenter.getPostsWithSearchQuery(1, query)

    }


    override fun setSearchQueryInfo(query: String) {
        binding.layoutSearchQueryInfo.visibility = View.VISIBLE
        binding.txtSearchQuery.text = "\"$query\" için sonuçlar aranıyor"
        adapter.setDataList(emptyList())
    }

    override fun fillPostList(result: List<PostItem>) {
        adapter.setDataList(result)
    }

    override fun setupPaginationPageCounts(currentPage: Int, header_wp_totalpages: Int) {
        binding.paginationView.setPageCounts(currentPage, header_wp_totalpages)
    }

    override fun setupPaginationPageChangeListener(
        pageChangeListener: OnPageChangeRequestListener?
    ) {
        binding.paginationView.onPageChangeRequestListener = pageChangeListener

    }

    override fun updateSearchQueryInfoEmpty(query: String?) {
        binding.txtSearchQuery.text = "\"$query\" için sonuç bulunamadı!"
    }

    override fun updateSearchQueryInfoNotEmpty(query: String?) {
        binding.txtSearchQuery.text = "\"$query\" için sonuçlar gösteriliyor"
    }


    override fun showDefaultLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideDefaultLoading() {
        binding.progressBar.visibility = View.GONE
    }
}