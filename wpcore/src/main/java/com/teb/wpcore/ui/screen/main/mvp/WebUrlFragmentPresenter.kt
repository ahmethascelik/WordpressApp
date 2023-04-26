package com.teb.wpcore.ui.screen.main.mvp

import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.service.PostsService
import com.teb.wpcore.ui.base.BasePresenter

class WebUrlFragmentPresenter(
    view: WebUrlFragmentView,
    val service: PostsService = ServiceLocator.providePostService()
) : BasePresenter<WebUrlFragmentView>(view) {


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