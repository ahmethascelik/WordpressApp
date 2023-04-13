package com.teb.wordpressapp.ui.screen.main.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.databinding.FragmentCategoryListBinding
import com.teb.wordpressapp.ui.BaseFragment

class CategoriesFragment: BaseFragment() {

    val service = ServiceLocator.providePostService()

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
        getCategories()
    }

    private fun getCategories() {
        val service = ServiceLocator.providePostService()
        val adapter = CategoryAdapter()
        service.getTopLevelCategories().makeCall { categories ->
            binding.apply {
                categoriesRecyclerView.layoutManager = LinearLayoutManager(activity)
                if (categories != null){
                    adapter.setDataList(categories)
                }
                categoriesRecyclerView.adapter = adapter
            }
            /*categories?.get(3)?.id?.let { it1 -> service.getCategories(it1).makeCall(){
                    innerCat->
                Toast.makeText(activity, "size "+ innerCat?.size, Toast.LENGTH_SHORT).show()

            } }*/
        }
    }
}