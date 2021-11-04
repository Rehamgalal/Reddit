package com.scan.reddit.repositories

 import androidx.paging.PagingData
 import com.scan.reddit.db.PostEntity
 import com.scan.reddit.model.Children
 import io.reactivex.Flowable
 import io.reactivex.Observable
 import io.reactivex.Single
 import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    fun getPosts(searchKey:String): Flow<PagingData<PostEntity>>
    fun insert(postEntity: PostEntity)
    fun remove(postEntity: PostEntity)
    fun getLiked(): Observable<List<PostEntity>>
}