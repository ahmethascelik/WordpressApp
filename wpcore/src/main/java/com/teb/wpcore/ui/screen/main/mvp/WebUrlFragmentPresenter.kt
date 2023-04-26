package com.teb.wpcore.ui.screen.main.mvp

import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.service.PostsService
import com.teb.wpcore.ui.base.BasePresenter

class WebUrlFragmentPresenter : BasePresenter<WebUrlFragmentView> {


    val service: PostsService

    constructor(
        view: WebUrlFragmentView,
        service: PostsService = ServiceLocator.providePostService()
    ) : super(view) {
        this.service = service
    }

    fun getPostsOfSlug(slug: String, url: String) {
        service.getPostsOfSlug(slug).makeCall { list->

            if(list != null && list.isNotEmpty()){
                val postItem = list[0]

                view.openPostDetail(postItem)

            }else{
                view.openWebUrl(url)
            }


        }

    }
}