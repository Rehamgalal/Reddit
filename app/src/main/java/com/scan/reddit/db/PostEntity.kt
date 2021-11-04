package com.scan.reddit.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scan.reddit.model.Media


@Entity(tableName = "posts")
data class PostEntity(
    val id: String,
    val author: String,
    val content: String?,
    val title: String?,
    @PrimaryKey val url: String,
    val urlToImage: String,
    val isVideo:Boolean,
    val videoUrl:Media?,
    var fav: Int
)
