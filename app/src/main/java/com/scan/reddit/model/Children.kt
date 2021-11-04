package com.scan.reddit.model

import com.scan.reddit.db.PostEntity

data class Children(
    val `data`: DataX,
    val kind: String
)



    fun Children.toPostEntity(): PostEntity {
        val content = data.author_flair_text ?: "no content"
        val redditVideo:RedditVideo
           if(data.media!=null) {
            redditVideo = data.media.reddit_video ?: RedditVideo("",0,"",0,"",false,"","",0)
        } else {
             redditVideo =  RedditVideo("",0,"",0,"",false,"","",0)
        }

        return PostEntity(data.id,data.author,content,data.title,data.url,data.thumbnail,data.is_video,redditVideo,0)
    }