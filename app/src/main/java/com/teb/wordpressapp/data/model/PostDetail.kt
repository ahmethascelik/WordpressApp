package com.teb.wordpressapp.data.model

data class PostDetail(
    val slug : String?,
    val title : PostItemTitle?,
    val yoast_head_json : YoastHeadJson?,
    val content: PostContent?,
    val link: String?
) {
    fun title(): String {
        return yoast_head_json?.title ?: ""
    }

    fun description(): String {
        return yoast_head_json?.description ?: ""
    }

    fun imageUrl(): String {
        return yoast_head_json?.og_image?.first()?.url ?: ""
    }

    fun content() : String{
        return content?.rendered ?: ""
    }
}

data class PostContent(
    val rendered : String?
)