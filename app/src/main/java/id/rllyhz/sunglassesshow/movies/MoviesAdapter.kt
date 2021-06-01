package id.rllyhz.sunglassesshow.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.rllyhz.core.api.ApiEndpoint.Companion.IMAGE_URL
import id.rllyhz.core.domain.model.Movie
import id.rllyhz.sunglassesshow.R
import id.rllyhz.sunglassesshow.databinding.ItemMovieAndTvShowListBinding

class MoviesAdapter : ListAdapter<Movie, MoviesAdapter.MoviesViewHolder>(MovieComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val binding = ItemMovieAndTvShowListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        with(getItem(position)) {
            if (this != null) holder.bind(this)
        }
    }

    inner class MoviesViewHolder(
        private val binding: ItemMovieAndTvShowListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                val fullTitle = itemView.context.getString(
                    R.string.title_format,
                    movie.title,
                    movie.year.toString()
                )

                with(ivItemList) {
                    Glide.with(context)
                        .load(IMAGE_URL + movie.posterPath)
                        .placeholder(R.drawable.bg_poster_placeholder)
                        .error(R.drawable.bg_poster_error)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(this)

                    contentDescription = fullTitle
                }

                rbItemList.rating = movie.rating
                tvItemListTitle.text = fullTitle

                itemView.setOnClickListener { movieItemCallback?.onClick(movie) }
            }
        }
    }

    class MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldMovie: Movie, newMovie: Movie): Boolean =
            oldMovie.id == newMovie.id

        override fun areContentsTheSame(oldMovie: Movie, newMovie: Movie): Boolean =
            oldMovie == newMovie
    }

    interface MovieItemCallback {
        fun onClick(movie: Movie)
    }

    private var movieItemCallback: MovieItemCallback? = null

    fun setItemCallback(callback: MovieItemCallback?) {
        movieItemCallback = callback
    }
}