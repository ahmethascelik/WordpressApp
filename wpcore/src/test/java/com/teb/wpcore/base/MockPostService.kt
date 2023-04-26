package com.teb.wpcore.base

import com.teb.wpcore.data.model.Category
import com.teb.wpcore.data.model.Comment
import com.teb.wpcore.data.model.PostDetail
import com.teb.wpcore.data.model.PostItem
import com.teb.wpcore.data.service.PostsService
import retrofit2.Call

interface MockPostService : PostsService {
    override fun getPosts(search: String?, page: String?): Call<List<PostItem>?> {
        TODO("Not yet implemented")
    }

    override fun getPostsOfCategory(categoryId: String?, page: String?): Call<List<PostItem>?> {
        TODO("Not yet implemented")
    }

    override fun getPostsOfSlugsCommaSeperated(
        slugsCommaSeperated: String?,
        page: String?,
    ): Call<List<PostItem>?> {
        TODO("Not yet implemented")
    }

    override fun getPostsOfSlug(slug: String): Call<List<PostItem>?> {
        TODO("Not yet implemented")
    }

    override fun getPostWithId(id: String): Call<PostDetail> {
        TODO("Not yet implemented")
    }

    override fun getPageWithId(id: String): Call<PostDetail> {
        TODO("Not yet implemented")
    }

    override fun getCommentsWithPostId(postId: String): Call<List<Comment>?> {
        TODO("Not yet implemented")
    }

    override fun getCategories(parent: String): Call<List<Category>?> {
        TODO("Not yet implemented")
    }

    override fun getTopLevelCategories(): Call<List<Category>?> {
        TODO("Not yet implemented")
    }
}