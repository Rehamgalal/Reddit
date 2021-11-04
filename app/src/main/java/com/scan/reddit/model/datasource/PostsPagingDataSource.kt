package com.scan.reddit.model.datasource

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.scan.reddit.api.RedditApi
import com.scan.reddit.db.PostEntity
import com.scan.reddit.model.Children
import com.scan.reddit.model.PostResponse
import com.scan.reddit.model.toPostEntity
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsPagingDataSource @Inject constructor(private val service:RedditApi,private val searchKey:String) : RxPagingSource<Int, PostEntity>(){
    private var after = ""

    override fun getRefreshKey(state: PagingState<Int, PostEntity>): Int? {
        return state.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, PostEntity>> {
        val pageNumber = params.key ?: 1
        val limit = 20
        return if (searchKey == "") {
            service.getTopPosts("all",limit,after).subscribeOn(Schedulers.io()).map {
            after = it.data.after
            toLoadResult(it.data.children.map { it.toPostEntity() },pageNumber)
        }.doOnSuccess {

            }.onErrorReturn {
            LoadResult.Error(it)
        } }
        else{
            service.getSearchPosts(searchKey,limit,after).subscribeOn(Schedulers.io()).map {
                after = it.data.after
                toLoadResult(it.data.children.map { it.toPostEntity() },pageNumber)
            }.onErrorReturn {
                LoadResult.Error(it)
            }
        }
    }


    private fun toLoadResult(data: List<PostEntity>, position: Int): LoadResult<Int,PostEntity> {
        return LoadResult.Page(
            data = data,
            prevKey = if (position == 1) null else position - 1,
            nextKey = position + 1
        )
    }
}
