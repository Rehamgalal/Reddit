package com.scan.reddit.model

import com.scan.reddit.db.PostEntity

data class Children(
    val `data`: DataX,
    val kind: String
)



    fun Children.toPostEntity(): PostEntity {
        val content = data.author_flair_text ?: "no content"
        val media = data.media ?: Media(RedditVideo("",0,"",0,"",false,"","",0))
        return PostEntity(data.id,data.author,content,data.title,data.url,data.thumbnail,data.is_video,media,0)
    }