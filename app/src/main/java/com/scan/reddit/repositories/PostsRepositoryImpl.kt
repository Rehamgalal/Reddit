package com.scan.reddit.repositories

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.scan.reddit.api.RedditApi
import com.scan.reddit.db.FavPostsDatabase
import com.scan.reddit.db.PostEntity
import com.scan.reddit.model.datasource.PostsPagingDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(private val api: RedditApi, private val database: FavPostsDatabase): PostsRepository {
    override fun getPosts(searchKey:String) =   Pager(config = PagingConfig(20,5,true,10,100)
        ,1,{PostsPagingDataSource(api,searchKey)}).flow

    override fun insert(postEntity: PostEntity) {
        Completable.fromRunnable {
            val id = database.articlesDao().insert(postEntity)
            Log.i("inserted",id.toString())
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun remove(postEntity: PostEntity) {
        Completable.fromRunnable {
            val  id = database.articlesDao().deleteById(postEntity.id)
            Log.i("inserted",id.toString())
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun getLiked(): List<PostEntity> {
        return database.articlesDao().allPostsEntities()

    }

}