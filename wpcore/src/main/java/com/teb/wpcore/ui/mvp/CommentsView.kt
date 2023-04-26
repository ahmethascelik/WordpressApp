package com.teb.wpcore.ui.mvp

import com.teb.wpcore.data.model.Comment
import com.teb.wpcore.ui.base.BaseView

interface CommentsView : BaseView {
    fun fillCommentList(commentList: List<Comment>?)
}