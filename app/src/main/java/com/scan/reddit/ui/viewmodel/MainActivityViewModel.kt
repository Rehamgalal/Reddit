package com.scan.reddit.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.scan.reddit.Reddit
import com.scan.reddit.api.RedditApi
import com.scan.reddit.db.FavPostsDatabase
import com.scan.reddit.db.PostEntity
import com.scan.reddit.model.Children
import com.scan.reddit.repositories.PostsRepository
import com.scan.reddit.repositories.PostsRepositoryImpl
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainActivityViewModel(@NonNull application: Application) : AndroidViewModel(application) {
    private val repository:PostsRepository
    var listResult: Flow<PagingData<PostEntity>>
    var likedList: MutableLiveData<List<PostEntity>> = MutableLiveData()
    private var searchKey= MutableStateFlow("")

    @Inject
    lateinit var  service: RedditApi

    @Inject
    lateinit var dataBase: FavPostsDatabase

    init {
        (application as Reddit).getAppComponent().inject(this)
        repository = PostsRepositoryImpl(service,dataBase)
        listResult  = searchKey.flatMapLatest {
            getListPosts(it)
        }
    }
    private fun getListPosts(searchKey:String) : Flow<PagingData<PostEntity>> {
        return  repository.getPosts(searchKey)
    }
    fun insert(postEntity: PostEntity) {
        repository.insert(postEntity)
    }
    fun remove(postEntity: PostEntity) {
        repository.remove(postEntity)
    }
    fun getLikedArticles(){
        Observable.fromCallable {
            repository.getLiked()
        }.subscribeOn(Schedulers.io()).map {
            likedList.postValue(it)
                }.doOnError {
                    Log.e("Error", it.message?:"")
                }.subscribe()
            }

    fun setFilter(filter: String) {
       searchKey.value = filter
    }
}