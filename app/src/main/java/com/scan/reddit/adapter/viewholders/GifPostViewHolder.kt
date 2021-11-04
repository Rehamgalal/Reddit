package com.scan.reddit.adapter.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.scan.reddit.R
import com.scan.reddit.databinding.ItemGifPostBinding
import com.scan.reddit.db.PostEntity
import com.scan.reddit.utils.OnPostLiked

class GifPostViewHolder(private val  binding: ItemGifPostBinding, private val listener: OnPostLiked): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostEntity){
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