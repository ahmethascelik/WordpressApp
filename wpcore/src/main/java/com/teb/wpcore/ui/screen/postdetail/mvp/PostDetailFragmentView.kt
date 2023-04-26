package com.teb.wpcore.ui.screen.postdetail.mvp

import com.teb.wpcore.ui.base.BaseView

interface PostDetailFragmentView : BaseView {

    fun loadHtmlContent(htmlContent : String)
}