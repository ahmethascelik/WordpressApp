package com.teb.wordpressapp.data.model

import android.text.TextUtils

data class PostItem(
    val id: String?,
    val slug : String?,
    val title : PostItemTitle?,
    val yoast_head_json : YoastHeadJson?
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
}

data class PostDetail(
    val slug : String?,
    val title : PostItemTitle?,
    val yoast_head_json : YoastHeadJson?
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
}

data class PostItemTitle(
    val rendered : String?
)

data class YoastHeadJson(
    val og_image : List<OgImage?>?,
    val title : String?,
    val description: String?
)

data class OgImage(
    val url : String?
)