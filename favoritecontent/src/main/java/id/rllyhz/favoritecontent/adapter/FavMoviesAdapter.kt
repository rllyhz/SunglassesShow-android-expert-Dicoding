package id.rllyhz.favoritecontent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.rllyhz.core.api.ApiEndpoint.Companion.IMAGE_URL
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.favoritecontent.utils.FavItemCallback
import id.rllyhz.sunglassesshow.R
import id.rllyhz.sunglassesshow.databinding.ItemMovieAndTvShowListBinding

class FavMoviesAdapter :
    PagedListAdapter<FavMovie, FavMoviesAdapter.FavMoviesViewHolder>(FavMovieComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMoviesViewHolder {
        val binding = ItemMovieAndTvShowListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavMoviesViewHolder, position: Int) {
        with(getItem(position)) {
            if (this != null) holder.bind(this)
        }
    }

    fun get(position: Int): FavMovie =
        getItem(position) ?: FavMovie(-1, "", "", "", 0, 0f)

    inner class FavMoviesViewHolder(
        private val binding: ItemMovieAndTvShowListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favMovie: FavMovie) {
            with(binding) {
                val fullTitle = itemView.context.getString(
                    R.string.title_format,
                    favMovie.title,
                    favMovie.year.toString()
                )

                with(ivItemList) {
                    Glide.with(context)
                        .load(IMAGE_URL + favMovie.backdropPath)
                        .placeholder(R.drawable.bg_poster_placeholder)
                        .error(R.drawable.bg_poster_error)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(this)

                    contentDescription = fullTitle
                }

                rbItemList.rating = favMovie.rating
                tvItemListTitle.text = fullTitle

                itemView.setOnClickListener { favMovieItemCallback?.onClick(favMovie) }
            }
        }
    }

    class FavMovieComparator : DiffUtil.ItemCallback<FavMovie>() {
        override fun areItemsTheSame(oldFavMovie: FavMovie, newFavMovie: FavMovie): Boolean =
            oldFavMovie.id == newFavMovie.id

        override fun areContentsTheSame(oldFavMovie: FavMovie, newFavMovie: FavMovie): Boolean =
            oldFavMovie == newFavMovie
    }

    private var favMovieItemCallback: FavItemCallback<FavMovie>? = null

    fun setItemCallback(callback: FavItemCallback<FavMovie>) {
        favMovieItemCallback = callback
    }
}