package com.teb.wpcore.ui.screen.postdetail.mvp

import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.model.PostDetail
import com.teb.wpcore.ui.screen.main.categories.mvp.BasePresenter


class PostDetailFragmentPresenter(view: PostDetailFragmentView) : BasePresenter<PostDetailFragmentView>(view) {

    var postDetail: PostDetail? = null

    val service = ServiceLocator.providePostService()


    fun getPostWithId(postId: String) {

        service.getPostWithId(postId).makeCall { postDetail ->

            this.postDetail = postDetail

            view.loadHtmlContent(postDetail.content())

        }



    }
}