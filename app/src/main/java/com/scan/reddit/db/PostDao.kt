package com.scan.reddit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articleList: List<PostEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articleItem: PostEntity)

    @Query("DELETE FROM posts WHERE id = :id")
    fun deleteById(id: String)

    @Query("SELECT * FROM posts ORDER BY id")
    fun allPostsEntities(): Flowable<List<PostEntity>>

    @Query("SELECT COUNT(*) FROM posts")
    fun getCount(): Int
}