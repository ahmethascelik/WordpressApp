package com.teb.wordpressapp.data.model

data class Comment(
    val id: String?,
    val author_name: String?,
    val content : CommentContent?,
    val date_gmt : String?,
    val parent: String?
)

data class CommentContent(
    val rendered : String?
)

