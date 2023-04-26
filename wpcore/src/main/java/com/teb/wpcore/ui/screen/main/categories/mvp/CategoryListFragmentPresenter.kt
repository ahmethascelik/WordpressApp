package com.teb.wpcore.ui.screen.main.categories.mvp

import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.service.PostsService
import com.teb.wpcore.ui.base.BasePresenter


class CategoryListFragmentPresenter(view: CategoryListView,
                                    val service: PostsService = ServiceLocator.providePostService()
) : BasePresenter<CategoryListView>(view) {

    fun getCategories() {
        service.getTopLevelCategories().makeCall { categories ->

            view.setList(categories)


        }

    }

}