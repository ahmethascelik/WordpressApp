package com.teb.wpcore.ui.screen.main.categories.mvp

import com.teb.wpcore.data.ServiceLocator


class CategoryListFragmentUnit(view: CategoryListView) : BaseUnit<CategoryListView>(view) {

    val service = ServiceLocator.providePostService()

    fun getCategories() {
        service.getTopLevelCategories().makeCall { categories ->

            view.setList(categories)


        }

    }

}