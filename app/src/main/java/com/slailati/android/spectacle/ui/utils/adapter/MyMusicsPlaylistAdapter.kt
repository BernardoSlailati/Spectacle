package com.slailati.android.spectacle.ui.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slailati.android.spectacle.R
import com.slailati.android.spectacle.databinding.ItemMyMusicBinding
import com.slailati.android.spectacle.domain.model.MusicModel

class MyMusicsPlaylistAdapter :
    ListAdapter<MusicModel, MyMusicsPlaylistAdapter.ViewHolder>(DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<MusicModel>() {
        override fun areItemsTheSame(
            oldItem: MusicModel,
            newItem: MusicModel,
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(
            oldItem: MusicModel,
            newItem: MusicModel,
        ): Boolean =
            oldItem.title == newItem.title &&
                    oldItem.artistName == newItem.artistName &&
                    oldItem.albumCoverUrl == newItem.albumCoverUrl
    }

    inner class ViewHolder(private val itemBinding: ItemMyMusicBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: MusicModel) {
            with(itemBinding) {
                tvTitle.text = item.title
                tvArtist.text = item.artistName
                Glide
                    .with(root)
                    .load(item.albumCoverUrl)
                    .placeholder(R.raw.loading_album)
                    .error(R.drawable.ic_not_found_album)
                    .centerCrop()
                    .into(ivAlbumCover)
            }
        }
    }

    fun removeAt(position: Int) {
        val updatedList = currentList.toMutableList()
        updatedList.remove(currentList[position])
        submitList(updatedList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMyMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = currentList.size

}