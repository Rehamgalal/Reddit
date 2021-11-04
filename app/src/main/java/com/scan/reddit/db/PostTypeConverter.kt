package com.scan.reddit.db

import androidx.room.TypeConverter
import com.scan.reddit.model.RedditVideo
import org.json.JSONObject

class PostTypeConverter {

    @TypeConverter
    fun fromSource(redditVideo: RedditVideo): String {
        return JSONObject().apply{
            put("dash_url",redditVideo.dash_url)
            put("duration",redditVideo.duration)
            put("fallback_url",redditVideo.fallback_url)
            put("height",redditVideo.height)
            put("hls_url",redditVideo.hls_url)
            put("is_gif",redditVideo.is_gif)
            put("scrubber_media_url",redditVideo.scrubber_media_url)
            put("transcoding_status",redditVideo.transcoding_status)
            put("width",redditVideo.width)
        }.toString()
    }

    @TypeConverter
    fun toSource(redditVideo: String): RedditVideo {
        val json = JSONObject(redditVideo)

        return if (!json.getString("dash_url").equals("")) {
            RedditVideo(json.getString("dash_url"),0,"",0,"",json.getBoolean("is_gif"),"","",0)
        } else {
            RedditVideo("",0,"",0,"",false,"","",0)
        }
    }
}