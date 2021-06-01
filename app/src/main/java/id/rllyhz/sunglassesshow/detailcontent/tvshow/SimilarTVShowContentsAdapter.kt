package id.rllyhz.sunglassesshow.detailcontent.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.rllyhz.core.api.ApiEndpoint.Companion.IMAGE_URL
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.sunglassesshow.R
import id.rllyhz.sunglassesshow.databinding.ItemSimilarContentsBinding
import id.rllyhz.sunglassesshow.detailcontent.ContentItemCallback

class SimilarTVShowContentsAdapter :
    ListAdapter<TVShow, SimilarTVShowContentsAdapter.TVShowContentViewHolder>(TVShowComparator()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TVShowContentViewHolder {
        val binding =
            ItemSimilarContentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowContentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TVShowContentViewHolder,
        position: Int
    ) {
        with(getItem(position)) {
            if (this != null) holder.bind(this)
        }
    }

    inner class TVShowContentViewHolder(
        private val binding: ItemSimilarContentsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tvShow: TVShow) {
            with(binding) {
                val fullTitle = root.context.getString(
                    R.string.title_format,
                    tvShow.title,
                    tvShow.year.toString()
                )

                Glide.with(itemView.context)
                    .load(IMAGE_URL + tvShow.posterPath)
                    .placeholder(R.drawable.bg_poster_placeholder)
                    .error(R.drawable.bg_poster_placeholder)
                    .into(ivItemSimilarContents)
                ivItemSimilarContents.contentDescription = fullTitle

                itemView.setOnClickListener { tvShowItemCallback?.onClick(tvShow) }
            }
        }
    }

    class TVShowComparator : DiffUtil.ItemCallback<TVShow>() {
        override fun areItemsTheSame(oldtvShow: TVShow, newTvShow: TVShow): Boolean =
            oldtvShow.id == newTvShow.id

        override fun areContentsTheSame(oldtvShow: TVShow, newTvShow: TVShow): Boolean =
            oldtvShow == newTvShow
    }

    private var tvShowItemCallback: ContentItemCallback<TVShow>? = null

    fun setItemCallback(callback: ContentItemCallback<TVShow>?) {
        tvShowItemCallback = callback
    }
}