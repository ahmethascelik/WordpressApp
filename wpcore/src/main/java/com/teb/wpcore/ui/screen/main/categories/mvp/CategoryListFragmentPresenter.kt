package com.teb.wpcore.ui.screen.main.categories.mvp

import com.teb.wpcore.data.ServiceLocator


class CategoryListFragmentPresenter(view: CategoryListView) : BasePresenter<CategoryListView>(view) {

    val service = ServiceLocator.providePostService()

    fun getCategories() {
        service.getTopLevelCategories().makeCall { categories ->

            view.setList(categories)


        }

    }

}