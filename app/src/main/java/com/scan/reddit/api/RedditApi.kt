package com.scan.reddit.api

import com.scan.reddit.model.PostResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {

    @GET("search.json")
    fun getTopPosts(@Query("q")searchKey:String,@Query("limit")pageSize:Int,@Query("after")after:String):Single<PostResponse>

    @GET("search.json")
    fun getSearchPosts(@Query("t")searchKey:String,@Query("limit")pageSize:Int,@Query("after")after:String):Single<PostResponse>

}