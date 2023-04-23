package com.teb.wordpressapp.ui.screen.main.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.teb.wordpressapp.data.ServiceLocator
import com.teb.wordpressapp.databinding.FragmentCategoryBinding
import com.teb.wordpressapp.ui.BaseFragment

class CategoryFragment: BaseFragment (){
    val service = ServiceLocator.providePostService()
    companion object{
        fun newInstance(): CategoryFragment {
            val args = Bundle()
            val fragment = CategoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        defaultLoadingCallback = { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        makeInitialRequest()
    }

    private fun makeInitialRequest() {
        val adapter = CategoryAdapter()
        binding.recyclerView.adapter = adapter

        service.getTopLevelCategories().makeCall { categoryList ->
            categoryList?.let {
                adapter.setDataList(it)
            }
        }
    }
}
