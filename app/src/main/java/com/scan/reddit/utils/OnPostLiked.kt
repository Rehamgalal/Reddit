package com.scan.reddit.utils

import com.scan.reddit.db.PostEntity

interface OnPostLiked {
    fun onPostLiked(post: PostEntity)
}