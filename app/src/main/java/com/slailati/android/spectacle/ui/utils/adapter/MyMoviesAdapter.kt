package com.slailati.android.spectacle.ui.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slailati.android.spectacle.R
import com.slailati.android.spectacle.databinding.ItemMyMovieBinding
import com.slailati.android.spectacle.domain.model.MovieModel
import com.slailati.android.spectacle.domain.service.TheMovieDatabaseService
import com.slailati.android.spectacle.ui.extension.gone
import com.slailati.android.spectacle.ui.extension.visible

class MyMoviesAdapter(
    private val onItemClickListener: OnItemClickListener<MovieModel>,
) :
    ListAdapter<MovieModel, MyMoviesAdapter.ViewHolder>(DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel,
        ): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel,
        ): Boolean =
            oldItem.remoteId == newItem.remoteId &&
                    oldItem.title == newItem.title &&
                    oldItem.genreIds == newItem.genreIds &&
                    oldItem.posterPath == newItem.posterPath &&
                    oldItem.voteAverage == newItem.voteAverage
    }

    inner class ViewHolder(private val itemBinding: ItemMyMovieBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(position: Int, item: MovieModel) {
            with(itemBinding) {
                if (position == 0) {
                    cvAdd.visible()
                    cvVoteAverage.gone()
                    ivPosterBackground.background =
                        ContextCompat.getDrawable(itemBinding.root.context,
                            R.color.white_transparent)
                    tvTitle.text = itemBinding.root.context.getString(R.string.add_title)
                    clContent.setOnClickListener {
                        onItemClickListener.onAddButtonClick(item)
                    }
                    clContent.setOnLongClickListener { false }
                } else {
                    cvAdd.gone()
                    cvVoteAverage.visible()
                    clContent.setOnClickListener {}
                    clContent.setOnLongClickListener {
                        onItemClickListener.onLongClick(item, position)
                        true
                    }
                    tvVoteAverage.text = item.voteAverage.toString()
                    tvTitle.text = item.title
                    Glide
                        .with(root)
                        .load(TheMovieDatabaseService.BASE_IMAGE_POSTER_URL + item.posterPath)
                        .placeholder(R.raw.loading_new_movies)
                        .error(R.drawable.ic_not_found_album)
                        .centerCrop()
                        .into(ivPoster)
                }
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
            ItemMyMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = currentList[position]
        holder.bind(position, currentItem)
    }

    override fun getItemCount(): Int = currentList.size

}