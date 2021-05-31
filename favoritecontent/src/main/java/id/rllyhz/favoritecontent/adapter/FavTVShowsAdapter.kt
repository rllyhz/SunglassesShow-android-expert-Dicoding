package id.rllyhz.favoritecontent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.rllyhz.core.api.ApiEndpoint
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.favoritecontent.utils.FavItemCallback
import id.rllyhz.sunglassesshow.R
import id.rllyhz.sunglassesshow.databinding.ItemMovieAndTvShowListBinding

class FavTVShowsAdapter :
    PagedListAdapter<FavTVShow, FavTVShowsAdapter.FavTVShowViewHolder>(FavTVShowComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavTVShowViewHolder {
        val binding = ItemMovieAndTvShowListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavTVShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavTVShowViewHolder, position: Int) {
        with(getItem(position)) {
            if (this != null) holder.bind(this)
        }
    }

    fun get(position: Int): FavTVShow =
        getItem(position) ?: FavTVShow(-1, "", "", "", 0, 0f)

    inner class FavTVShowViewHolder(
        private val binding: ItemMovieAndTvShowListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favTVShow: FavTVShow) {
            with(binding) {
                val fullTitle = itemView.context.getString(
                    R.string.title_format,
                    favTVShow.title,
                    favTVShow.year.toString()
                )

                with(ivItemList) {
                    Glide.with(context)
                        .load(ApiEndpoint.IMAGE_URL + favTVShow.backdropPath)
                        .placeholder(R.drawable.bg_poster_placeholder)
                        .error(R.drawable.bg_poster_error)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(this)

                    contentDescription = fullTitle
                }

                rbItemList.rating = favTVShow.rating
                tvItemListTitle.text = fullTitle

                itemView.setOnClickListener { favTVShowItemCallback?.onClick(favTVShow) }
            }
        }
    }

    class FavTVShowComparator : DiffUtil.ItemCallback<FavTVShow>() {
        override fun areItemsTheSame(oldFavTVShow: FavTVShow, newFavTVShow: FavTVShow): Boolean =
            oldFavTVShow.id == newFavTVShow.id

        override fun areContentsTheSame(oldFavTVShow: FavTVShow, newFavTVShow: FavTVShow): Boolean =
            oldFavTVShow == newFavTVShow
    }

    private var favTVShowItemCallback: FavItemCallback<FavTVShow>? = null

    fun setItemCallback(callback: FavItemCallback<FavTVShow>) {
        favTVShowItemCallback = callback
    }
}