package com.teb.wpcore.ui.screen.main.mvp

import android.content.Intent
import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.ui.screen.main.categories.mvp.BasePresenter
import com.teb.wpcore.ui.screen.postdetail.PostDetailActivity

class WebUrlFragmentPresenter(view: WebUrlFragmentView) : BasePresenter<WebUrlFragmentView>(view) {


    val service = ServiceLocator.providePostService()

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