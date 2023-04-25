package com.teb.wpcore.ui.screen.main.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wpcore.data.model.Category
import com.teb.wpcore.databinding.FragmentCategoryListBinding
import com.teb.wpcore.ui.BaseFragment
import com.teb.wpcore.ui.screen.main.categories.mvp.CategoryListFragmentPresenter
import com.teb.wpcore.ui.screen.main.categories.mvp.CategoryListView

class CategoriesFragment: BaseFragment(), CategoryListView {

    val unit = CategoryListFragmentPresenter(this)


    companion object{

        fun newInstance(): CategoriesFragment {
            val args = Bundle()
            val fragment = CategoriesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentCategoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCategoryListBinding.inflate(layoutInflater, container, false)

        initView()
        makeInitialRequests()
        return binding.root
    }

    val adapter = CategoryAdapter()

    private fun initView() {

        adapter.postItemTitleClickListener = { category ->

            if (activity is CategoryListFragmentActionListenerActivity) {
                (activity as CategoryListFragmentActionListenerActivity).onCategorySelected(category)
            }
        }
    }

    private fun makeInitialRequests() {
        unit.getCategories()
    }

    override fun setList(dataList: List<Category>?) {
        binding.apply {
            categoriesRecyclerView.layoutManager = LinearLayoutManager(activity)
            if (dataList != null){
                adapter.setDataList(dataList)
            }
            categoriesRecyclerView.adapter = adapter
        }
    }


    override fun showDefaultLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideDefaultLoading() {
        binding.progressBar.visibility = View.GONE
    }

}