package com.teb.wpcore.ui.screen.main.postitem.mvp

import com.teb.wpcore.data.model.PostItem
import com.teb.wpcore.ui.base.BaseView
import com.teb.wpcore.ui.widget.OnPageChangeRequestListener

interface PostItemListView : BaseView {
    fun fillPostList(result: List<PostItem>)
    fun setupPaginationPageCounts(currentPage: Int, header_wp_totalpages: Int)
    fun updateSearchQueryInfoEmpty(query: String?)
    fun updateSearchQueryInfoNotEmpty(query: String?)
    fun setupPaginationPageChangeListener(pageChangeListener: OnPageChangeRequestListener?)
    fun setSearchQueryInfo(query: String)
}