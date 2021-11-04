package com.scan.reddit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scan.reddit.R
import com.scan.reddit.adapter.viewholders.GifPostViewHolder
import com.scan.reddit.adapter.viewholders.ImagePostViewHolder
import com.scan.reddit.adapter.viewholders.VideoPostViewHolder
import com.scan.reddit.databinding.ItemGifPostBinding
import com.scan.reddit.databinding.ItemImagePostBinding
import com.scan.reddit.databinding.ItemVideoPostBinding
import com.scan.reddit.db.PostEntity
import com.scan.reddit.utils.OnPostLiked

class LikedListAdapter(private val list: List<PostEntity>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener: OnPostLiked? = null
    fun setListener(listener: OnPostLiked) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_image_post -> (holder as ImagePostViewHolder).bind(getItem(position))
            R.layout.item_video_post -> (holder as VideoPostViewHolder).bind(getItem(position))
            R.layout.item_gif_post -> (holder as GifPostViewHolder).bind(getItem(position))
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_image_post -> ImagePostViewHolder(
                ItemImagePostBinding.inflate(
                LayoutInflater.from(parent.context),parent,false), listener!!)
            R.layout.item_video_post -> VideoPostViewHolder(
                ItemVideoPostBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false), listener!!)
            R.layout.item_gif_post -> GifPostViewHolder(
                ItemGifPostBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false), listener!!)
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    private fun getItem(position: Int): PostEntity{
        return list[position]
    }
    override fun getItemViewType(position: Int): Int {
        return  if (getItem(position).isVideo) {
            if (getItem(position).videoUrl!!.is_gif) {
                R.layout.item_gif_post
            } else {
                R.layout.item_video_post
            }
        } else {
            R.layout.item_image_post
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}
