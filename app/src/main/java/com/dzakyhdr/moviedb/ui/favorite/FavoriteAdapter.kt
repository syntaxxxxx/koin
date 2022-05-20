package com.dzakyhdr.moviedb.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dzakyhdr.moviedb.R
import com.dzakyhdr.moviedb.data.local.favorite.MovieEntity
import com.dzakyhdr.moviedb.databinding.ItemPopularBinding
import com.dzakyhdr.moviedb.utils.urlImage

class FavoriteAdapter : ListAdapter<MovieEntity, FavoriteAdapter.ViewHolder>(DiffCallBack()) {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPopularBinding.bind(view)

        fun bind(dataPopular: MovieEntity) {
            binding.apply {
                binding.tvTitle.text = dataPopular.title
                binding.tvReleaseDate.text = dataPopular.releaseDate
                binding.tvOverview.text = dataPopular.overview
                Glide.with(binding.root).load(urlImage + dataPopular.posterPath)
                    .error(R.drawable.ic_broken)
                    .into(binding.ivPoster)
                root.setOnClickListener {
                    val id = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(dataPopular.id)
                    it.findNavController().navigate(id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_popular, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}

class DiffCallBack: DiffUtil.ItemCallback<MovieEntity>(){
    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }

}