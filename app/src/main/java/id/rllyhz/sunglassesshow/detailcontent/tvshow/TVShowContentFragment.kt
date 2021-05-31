package id.rllyhz.sunglassesshow.detailcontent.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.core.api.ApiEndpoint.Companion.IMAGE_URL
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.core.utils.ext.getDateInString
import id.rllyhz.core.vo.StatusRes
import id.rllyhz.sunglassesshow.R
import id.rllyhz.sunglassesshow.detailcontent.ContentFragment
import id.rllyhz.sunglassesshow.detailcontent.ContentItemCallback
import id.rllyhz.sunglassesshow.detailcontent.DetailActivity
import id.rllyhz.sunglassesshow.utils.TVShowDetailViewModelAssistedFactory
import javax.inject.Inject

@AndroidEntryPoint
class TVShowContentFragment :
    ContentFragment<TVShow, SimilarTVShowContentsAdapter.TVShowContentViewHolder>(),
    ContentItemCallback<TVShow> {

    @Inject
    lateinit var assistedFactory: TVShowDetailViewModelAssistedFactory

    private val viewModel: TVShowDetailViewModel by viewModels {
        val tvShow = arguments?.getParcelable<TVShow>(PARAMS_TV_SHOWS)
        val id = tvShow?.id ?: -1
        TVShowDetailViewModel.Factory(assistedFactory, id)
    }

    override fun initAdapter(): ListAdapter<TVShow, SimilarTVShowContentsAdapter.TVShowContentViewHolder> =
        SimilarTVShowContentsAdapter().apply {
            setItemCallback(this@TVShowContentFragment)
        }

    override fun initUI() {
        val currentTVShow = arguments?.getParcelable<TVShow>(PARAMS_TV_SHOWS)

        with(binding) {
            ivViewTrailerDetail.setOnClickListener { }
            btnWatchDetail.setOnClickListener { }

            toggleBtnFav.setOnClickListener {
                if (!toggleBtnFav.isChecked) {
                    // delete
                    viewModel.removeFavTVShow(currentTVShow)
                    showToast(getString(R.string.favorites_deleted_tv_show_message))
                } else {
                    // add
                    viewModel.addFavTVShow(currentTVShow)
                    showToast(getString(R.string.favorites_added_tv_show_message))
                }
            }
        }
    }

    override fun observeData() {
        with(viewModel) {
            getDetailTVShow().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    StatusRes.SUCCESS -> setupSuccessUI(resource.data)
                    StatusRes.LOADING -> setupLoadingUI()
                    StatusRes.ERROR -> setErrorUI()
                    else -> Unit
                }
            }

            isFavorite.observe(viewLifecycleOwner) { tvShow ->
                binding.toggleBtnFav.isChecked = tvShow != null
            }

            getSimilarTVShows().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    StatusRes.SUCCESS -> initRecyclerData(resource.data)
                    StatusRes.ERROR -> setNoResultsUI(true)
                    StatusRes.LOADING -> setLoadingSimilarContents(true)
                    else -> Unit
                }
            }
        }
    }

    override fun setupSuccessUI(data: TVShow?) {
        with(binding) {
            if (data != null) {
                val fullTitle = getString(
                    R.string.title_format,
                    data.title,
                    data.year.toString()
                )

                rbDetail.rating = data.rating
                tvTitleDetail.text = fullTitle
                tvDurationDetail.text = data.duration
                tvGenresDetail.text = data.genres
                tvRateDetail.text =
                    resources.getString(R.string.rate_format, data.rate.toString())
                tvStatusDetail.text =
                    resources.getString(R.string.status_format, data.status ?: "")
                tvReleasedAtDetail.text =
                    resources.getString(R.string.released_at_format, data.getDateInString())
                tvSynopsisDetail.text = data.synopsis

                Glide.with(root)
                    .load(IMAGE_URL + data.backdropPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.bg_poster_error)
                    .placeholder(R.drawable.bg_poster_placeholder)
                    .into(ivPosterBgDetail)

                Glide.with(root)
                    .load(IMAGE_URL + data.posterPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.bg_poster_error)
                    .placeholder(R.drawable.bg_poster_placeholder)
                    .into(ivPosterDetail)

                ivPosterBgDetail.contentDescription = fullTitle
                ivPosterDetail.contentDescription = fullTitle

                showUI(true)
                isLoading = false
            }
        }
    }

    override fun onClick(item: TVShow) {
        with(Intent(requireActivity() as AppCompatActivity, DetailActivity::class.java)) {
            putExtra(DetailActivity.EXTRA_CONTENT_TV_SHOW, item)
            putExtra(DetailActivity.GOTO_TV_SHOW_DETAIL, true)

            requireActivity().startActivity(this)
        }
    }

    companion object {
        val TAG: String = TVShowContentFragment::class.java.simpleName

        fun newInstance(tvShow: TVShow): TVShowContentFragment =
            TVShowContentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAMS_TV_SHOWS, tvShow)
                }
            }
    }
}