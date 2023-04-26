package com.teb.wpcore.ui.mvp

import com.teb.wpcore.data.ServiceLocator
import com.teb.wpcore.data.service.PostsService
import com.teb.wpcore.ui.base.BasePresenter

class CommentsPresenter(view: CommentsView,
                        val service: PostsService = ServiceLocator.providePostService()) : BasePresenter<CommentsView>(view) {

    fun getCommentsWithPostId(postId: String) {
        service.getCommentsWithPostId(postId).makeCall { commentList ->
            view.fillCommentList(commentList)
        }
    }
}