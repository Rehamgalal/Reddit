package com.scan.reddit.db

import androidx.room.TypeConverter
import com.scan.reddit.model.Media
import com.scan.reddit.model.RedditVideo
import org.json.JSONObject

class PostTypeConverter {

    @TypeConverter
    fun fromSource(media: Media): String {
        return JSONObject().apply {
            put("reddit_video",media.reddit_video)
        }.toString()
    }

    @TypeConverter
    fun toSource(media: String): Media {
        val json = JSONObject(media)
        val redditVideo:RedditVideo = if (!json.getJSONObject("reddit_video").equals("")) {
            if (!json.getJSONObject("reddit_video").getString("dash_url").equals("")) {
                RedditVideo(json.getJSONObject("reddit_video").getString("dash_url"),0,"",0,"",false,"","",0)
            } else {
                RedditVideo("",0,"",0,"",false,"","",0)
            }
        } else {
            RedditVideo("",0,"",0,"",false,"","",0)
        }
      return Media(redditVideo)
    }
}