package com.teb.wpcore.ui.screen.main.mvp

import com.teb.wpcore.data.model.PostItem
import com.teb.wpcore.ui.base.BaseView

interface WebUrlFragmentView : BaseView {
    fun openPostDetail(postItem: PostItem)
    fun openWebUrl(url: String)
}