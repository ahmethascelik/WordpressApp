package com.teb.wpcore.ui.screen.main.categories.mvp

import com.teb.wpcore.data.model.Category
import com.teb.wpcore.ui.base.BaseView

interface CategoryListView : BaseView {

    fun setList(dataList : List<Category>?)
}