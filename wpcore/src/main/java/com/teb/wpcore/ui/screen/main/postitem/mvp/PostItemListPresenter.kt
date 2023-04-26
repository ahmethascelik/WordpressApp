package com.teb.wpcore.ui.screen.main.postitem.mvp

import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.model.Category
import com.teb.wpcore.data.model.PostItem
import com.teb.wpcore.data.service.PostsService
import com.teb.wpcore.ui.base.BasePresenter

class PostItemListPresenter(view: PostItemListView,
                            val service: PostsService = ServiceLocator.providePostService()
) : BasePresenter<PostItemListView>(view) {

    fun getPostWithPageNum(currentPage: Int) {

        service.getPosts(page = "" + currentPage)
            .makeCall(successCallback = { result: List<PostItem>? ->

                view.fillPostList(result!!)
            }, paginationCallback = { header_wp_totalpages ->

                view.setupPaginationPageCounts(currentPage, header_wp_totalpages)
                view.setupPaginationPageChangeListener { page ->
                    getPostWithPageNum(page)
                }
            })
    }


    fun getPostOfCategoryWithPageNum( currentPage: Int, category : Category? = null ) {

        service.getPostsOfCategory(categoryId = category?.id ,page = "" + currentPage)
            .makeCall(successCallback = { result: List<PostItem>? ->
                view.fillPostList(result!!)
            }, paginationCallback = { header_wp_totalpages ->
                view.setupPaginationPageCounts(currentPage, header_wp_totalpages)
                view.setupPaginationPageChangeListener { page ->
                    getPostOfCategoryWithPageNum(page, category)
                }
            })
    }

    fun getPostsWithSearchQuery(currentPage: Int, query: String) {
        view.setSearchQueryInfo(query)

        service.getPosts(search = query).makeCall(successCallback = { result: List<PostItem>? ->
            view.fillPostList(result!!)

            if (result.isEmpty()) {
                view.updateSearchQueryInfoEmpty(query)

            } else {

                view.updateSearchQueryInfoNotEmpty(query)

            }
        }, paginationCallback = { header_wp_totalpages ->

            view.setupPaginationPageCounts(currentPage, header_wp_totalpages)
            view.setupPaginationPageChangeListener { page ->
                getPostsWithSearchQuery(currentPage, query)
            }
        })
    }
}