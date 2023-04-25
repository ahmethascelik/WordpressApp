package com.teb.wpcore.ui.screen.postdetail.mvp

import com.teb.wpcore.ui.screen.main.categories.mvp.BaseView

interface PostDetailFragmentView : BaseView {

    fun loadHtmlContent(htmlContent : String)
}