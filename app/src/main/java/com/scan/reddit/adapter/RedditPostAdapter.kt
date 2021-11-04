package com.scan.reddit.adapter

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scan.reddit.R
import com.scan.reddit.databinding.ItemGifPostBinding
import com.scan.reddit.databinding.ItemImagePostBinding
import com.scan.reddit.databinding.ItemVideoPostBinding
import com.scan.reddit.db.PostEntity
import com.scan.reddit.model.Children
import com.scan.reddit.utils.OnPostLiked
import javax.inject.Inject

class RedditPostAdapter @Inject constructor() : PagingDataAdapter<PostEntity, RecyclerView.ViewHolder>(PostComparator) {

    private var listener: OnPostLiked ? = null
    fun setListener(listener: OnPostLiked) {
        this.listener = listener
    }
    object PostComparator : DiffUtil.ItemCallback<PostEntity>() {
        override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity) =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_image_post -> (holder as ImagePostViewHolder).bind(getItem(position)!!)
            R.layout.item_video_post -> (holder as VideoPostViewHolder).bind(getItem(position)!!)
            R.layout.item_gif_post -> (holder as GifPostViewHolder).bind(getItem(position)!!)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_image_post -> ImagePostViewHolder(ItemImagePostBinding.inflate(
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

    override fun getItemViewType(position: Int): Int {
        return  if (getItem(position)!!.isVideo) {
            if (getItem(position)!!.videoUrl!!.reddit_video.is_gif) {
                R.layout.item_gif_post
            } else {
                R.layout.item_video_post
            }
        } else {
            R.layout.item_image_post
        }
    }
    inner class ImagePostViewHolder(private val  binding: ItemImagePostBinding,private val listener: OnPostLiked): RecyclerView.ViewHolder(binding.root) {
        fun bind(item:PostEntity){
            Glide.with(itemView.context)
                .load(item.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(binding.imageView)
            binding.title.text = item.title
            binding.description.text = item.content ?: ""
            if (item.fav == 0) binding.like.setImageResource(R.drawable.like) else binding.like.setImageResource(R.drawable.liked)
            binding.like.setOnClickListener {
                listener.onPostLiked(item)
            }
        }
    }
    inner class GifPostViewHolder(private val  binding: ItemGifPostBinding,private val listener: OnPostLiked): RecyclerView.ViewHolder(binding.root) {
        fun bind(item:PostEntity){
            Glide.with(itemView.context)
                .load(item.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(binding.imageView)
            binding.title.text = item.title
            binding.description.text = item.content
            if (item.fav == 0) binding.like.setImageResource(R.drawable.like) else binding.like.setImageResource(R.drawable.liked)
            binding.like.setOnClickListener {
                listener.onPostLiked(item)
            }
        }
    }
    inner class VideoPostViewHolder(private val  binding: ItemVideoPostBinding,private val listener: OnPostLiked): RecyclerView.ViewHolder(binding.root) {
        fun bind(item:PostEntity){
            Glide.with(itemView.context)
                .load(item.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(binding.imageView)
            binding.title.text = item.title
            binding.description.text = item.content
            if (item.fav == 0) binding.like.setImageResource(R.drawable.like) else binding.like.setImageResource(R.drawable.liked)
            binding.like.setOnClickListener {
                if (item.fav == 0) {
                    item.fav = 1
                    binding.like.setImageResource(R.drawable.liked)
                } else {
                    item.fav = 0
                    binding.like.setImageResource(R.drawable.like)
                }
                listener.onPostLiked(item)
            }
        }
    }
}