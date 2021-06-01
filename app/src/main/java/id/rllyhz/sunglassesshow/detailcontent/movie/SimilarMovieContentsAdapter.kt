package id.rllyhz.sunglassesshow.detailcontent.movie

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
import id.rllyhz.sunglassesshow.databinding.ItemSimilarContentsBinding
import id.rllyhz.sunglassesshow.detailcontent.ContentItemCallback

class SimilarMovieContentsAdapter :
    ListAdapter<Movie, SimilarMovieContentsAdapter.SimilarContentViewHolder>(MovieComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimilarContentViewHolder {
        val binding =
            ItemSimilarContentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarContentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SimilarContentViewHolder,
        position: Int
    ) {
        getItem(position).apply {
            if (this != null) holder.bind(this)
        }
    }

    inner class SimilarContentViewHolder(
        private val binding: ItemSimilarContentsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                val fullTitle = root.context.getString(
                    R.string.title_format,
                    movie.title,
                    movie.year.toString()
                )

                Glide.with(itemView)
                    .load(IMAGE_URL + movie.backdropPath)
                    .error(R.drawable.bg_poster_error)
                    .placeholder(R.drawable.bg_poster_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivItemSimilarContents)
                ivItemSimilarContents.contentDescription = fullTitle

                itemView.setOnClickListener { movieItemCallback?.onClick(movie) }
            }
        }
    }

    class MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }

    private var movieItemCallback: ContentItemCallback<Movie>? = null

    fun setItemCallback(callback: ContentItemCallback<Movie>?) {
        movieItemCallback = callback
    }
}