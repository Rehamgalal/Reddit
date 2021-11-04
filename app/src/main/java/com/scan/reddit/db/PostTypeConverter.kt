package com.scan.reddit.db

import androidx.room.TypeConverter
import com.scan.reddit.model.Media
import com.scan.reddit.model.RedditVideo
import org.json.JSONObject

class PostTypeConverter {

    @TypeConverter
    fun fromSource(media: Media): String {
        val redditVideo = JSONObject().apply{
            put("dash_url",media.reddit_video!!.dash_url)
            put("duration",media.reddit_video.duration)
            put("fallback_url",media.reddit_video.fallback_url)
            put("height",media.reddit_video.height)
            put("hls_url",media.reddit_video.hls_url)
            put("is_gif",media.reddit_video.is_gif)
            put("scrubber_media_url",media.reddit_video.scrubber_media_url)
            put("transcoding_status",media.reddit_video.transcoding_status)
            put("width",media.reddit_video.width)
        }
        return JSONObject().apply {
            put("reddit_video",redditVideo)
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