package com.teb.wpcore.ui.screen.pagedetail.mvp

import com.teb.wpcore.ui.screen.main.categories.mvp.BaseView

interface PageDetailView : BaseView{
    fun loadHtml(content: String)
}