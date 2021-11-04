package com.scan.reddit.model

data class Media(
    val reddit_video: RedditVideo
) {
    companion object {
        operator fun invoke(
            reddit_video: RedditVideo? = null,
        ): Media {
            return Media(
                reddit_video ?: RedditVideo("",0,"",0,"",false,"","",0),
            )
        }
    }
}