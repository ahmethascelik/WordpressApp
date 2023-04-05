package com.teb.wordpressapp.data.model

import android.text.TextUtils

data class PostItem(
    val slug : String?,
    val title : PostItemTitle?,
    val yoast_head_json : YoastHeadJson?
) {
    fun renderedTitle(): String {
        return title?.rendered ?: ""
    }

    fun imageUrl(): String {
        return yoast_head_json?.og_image?.first()?.url ?: ""
    }
}

data class PostItemTitle(
    val rendered : String?
)

data class YoastHeadJson(
    val og_image : List<OgImage?>?
)

data class OgImage(
    val url : String?
)