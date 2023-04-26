package com.teb.wpcore.ui.screen.main.mvp

import com.teb.wpcore.base.MockCall
import com.teb.wpcore.base.MockPostService
import com.teb.wpcore.data.model.PostItem
import com.teb.wpcore.ui.base.TryAgainCallback
import okhttp3.Request
import okio.Timeout
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebUrlFragmentPresenterTest {

    val mockCall: Call<List<PostItem>?> = object :  MockCall<List<PostItem>?> {

        override fun enqueue(callback: Callback<List<PostItem>?>) {

            val mockResponse : Response<List<PostItem>?> =  Response.success(mockResponseBody)
            callback.onResponse(this, mockResponse)

        }
    }

    var mockResponseBody = ArrayList<PostItem>()

    private var isShowLoadingCalled: Boolean = false
    var isPostDetailCalled : Boolean = false
    var isopenWebUrlCalled : Boolean = false

    lateinit var unit : WebUrlFragmentPresenter

    val view = object : WebUrlFragmentView{
        override fun openPostDetail(postItem: PostItem) {
            isPostDetailCalled = true
        }

        override fun openWebUrl(url: String) {
            isopenWebUrlCalled = true
        }

        override fun showDefaultLoading() {
            isShowLoadingCalled = true
        }

        override fun hideDefaultLoading() {

        }

        override fun <T> onServiceFailure(
            call: Call<T>,
            t: Throwable,
            tryAgainCallback: TryAgainCallback?,
        ) {
            TODO("Not yet implemented")
        }

    }

    val service = object : MockPostService {

        override fun getPostsOfSlug(slug: String): Call<List<PostItem>?> {
            return mockCall
        }

    }

    @Before
    fun setUp() {

        unit = WebUrlFragmentPresenter(view = view, service = service )

    }

    @After
    fun tearDown() {
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun `when user clicks weblink, if link is a post link then service should return at least 1 item,  it opens post detail`() {

        //mocking

        mockResponseBody = ArrayList()
        mockResponseBody.add(PostItem("1", yoast_head_json = null))

        assertEquals(false, isShowLoadingCalled)


        //execute
        val url = "https://minimalistbaker.com/strawberry-matcha-chia-pudding/"
        val slug = "strawberry-matcha-chia-pudding"
        unit.getPostsOfSlug(slug = slug, url = url)
        assertEquals(true, isShowLoadingCalled)


        //assertions

        assertEquals(true, isPostDetailCalled)
        assertEquals(false, isopenWebUrlCalled)
    }

    @Test
    fun `when user clicks weblink, if link is NOT a post link then it opens weburl`() {

        //mocking

        mockResponseBody = ArrayList()

        //execute
        val url = "https://minimalistbaker.com/strawberry-matcha-chia-pudding/"
        val slug = "strawberry-matcha-chia-pudding"
        unit.getPostsOfSlug(slug = slug, url = url)


        //assertions

        assertEquals(false, isPostDetailCalled)
        assertEquals(true, isopenWebUrlCalled)
    }

}