package id.rllyhz.sunglassesshow.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.rllyhz.core.api.ApiEndpoint.Companion.IMAGE_URL
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.sunglassesshow.R
import id.rllyhz.sunglassesshow.databinding.ItemMovieAndTvShowListBinding

class TVShowsAdapter : ListAdapter<TVShow, TVShowsAdapter.TVShowsViewHolder>(TVShowComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowsViewHolder {
        val binding = ItemMovieAndTvShowListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TVShowsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TVShowsViewHolder, position: Int) {
        with(getItem(position)) {
            if (this != null) holder.bind(this)
        }
    }

    inner class TVShowsViewHolder(
        private val binding: ItemMovieAndTvShowListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TVShow) {
            with(binding) {
                val fullTitle = itemView.context.getString(
                    R.string.title_format,
                    tvShow.title,
                    tvShow.year.toString()
                )

                with(ivItemList) {
                    Glide.with(context)
                        .load(IMAGE_URL + tvShow.posterPath)
                        .placeholder(R.drawable.bg_poster_placeholder)
                        .error(R.drawable.bg_poster_error)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(this)

                    contentDescription = fullTitle
                }

                rbItemList.rating = tvShow.rating
                tvItemListTitle.text = fullTitle

                itemView.setOnClickListener { tvShowItemCallback?.onClick(tvShow) }
            }
        }
    }

    class TVShowComparator : DiffUtil.ItemCallback<TVShow>() {
        override fun areItemsTheSame(oldTVShow: TVShow, newTVShow: TVShow): Boolean =
            oldTVShow.id == newTVShow.id

        override fun areContentsTheSame(oldTVShow: TVShow, newTVShow: TVShow): Boolean =
            oldTVShow == newTVShow
    }

    interface TVShowItemCallback {
        fun onClick(tvShow: TVShow)
    }

    private var tvShowItemCallback: TVShowItemCallback? = null

    fun setItemCallback(callback: TVShowItemCallback) {
        tvShowItemCallback = callback
    }
}