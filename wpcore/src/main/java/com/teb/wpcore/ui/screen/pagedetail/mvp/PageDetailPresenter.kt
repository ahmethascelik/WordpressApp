package com.teb.wpcore.ui.screen.pagedetail.mvp

import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.model.PostDetail
import com.teb.wpcore.ui.screen.main.categories.mvp.BasePresenter

class PageDetailPresenter(view: PageDetailView) : BasePresenter<PageDetailView>(view) {


    private lateinit var pageDetail: PostDetail
    val service = ServiceLocator.providePostService()
    fun getPageWithId(pageId: String) {
        service.getPageWithId(pageId).makeCall { pageDetail ->

            this.pageDetail = pageDetail
            view.loadHtml(pageDetail.content())
        }


    }
}