package com.teb.wpcore.ui.screen.postdetail.mvp

import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.model.PostDetail
import com.teb.wpcore.data.service.PostsService
import com.teb.wpcore.ui.base.BasePresenter


class PostDetailFragmentPresenter(view: PostDetailFragmentView,
                                  val service: PostsService = ServiceLocator.providePostService()
) : BasePresenter<PostDetailFragmentView>(view) {

    var postDetail: PostDetail? = null


    fun getPostWithId(postId: String) {

        service.getPostWithId(postId).makeCall { postDetail ->

            this.postDetail = postDetail

            view.loadHtmlContent(postDetail.content())

        }



    }
}