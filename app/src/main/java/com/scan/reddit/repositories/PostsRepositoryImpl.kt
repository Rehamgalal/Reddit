package com.scan.reddit.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.scan.reddit.api.RedditApi
import com.scan.reddit.db.FavPostsDatabase
import com.scan.reddit.db.PostEntity
import com.scan.reddit.model.datasource.PostsPagingDataSource
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(private val api: RedditApi, private val database: FavPostsDatabase): PostsRepository {
    override fun getPosts(searchKey:String) =   Pager(config = PagingConfig(20,5,true,10,100)
        ,1,{PostsPagingDataSource(api,searchKey)}).flow

    override fun insert(postEntity: PostEntity) {
        Completable.fromRunnable {
            database.articlesDao().insert(postEntity)
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun remove(postEntity: PostEntity) {
        Completable.fromRunnable {
            database.articlesDao().deleteById(postEntity.id)
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    override fun getLiked(): List<PostEntity> {
        return database.articlesDao().allPostsEntities()

    }

}