package com.teb.wpcore.ui.screen.pagedetail.mvp

import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.model.PostDetail
import com.teb.wpcore.data.service.PostsService
import com.teb.wpcore.ui.base.BasePresenter

class PageDetailPresenter(view: PageDetailView,
                          val service: PostsService = ServiceLocator.providePostService()
) : BasePresenter<PageDetailView>(view) {


    private lateinit var pageDetail: PostDetail
    fun getPageWithId(pageId: String) {
        service.getPageWithId(pageId).makeCall { pageDetail ->

            this.pageDetail = pageDetail
            view.loadHtml(pageDetail.content())
        }


    }
}