package id.rllyhz.sunglassesshow.detailcontent.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.core.api.ApiEndpoint
import id.rllyhz.core.domain.model.Movie
import id.rllyhz.core.utils.ext.getDateInString
import id.rllyhz.core.vo.StatusRes
import id.rllyhz.sunglassesshow.R
import id.rllyhz.sunglassesshow.detailcontent.ContentFragment
import id.rllyhz.sunglassesshow.detailcontent.ContentItemCallback
import id.rllyhz.sunglassesshow.detailcontent.DetailActivity
import id.rllyhz.sunglassesshow.utils.MovieDetailViewModelAssistedFactory
import javax.inject.Inject

@AndroidEntryPoint
class MovieContentFragment :
    ContentFragment<Movie, SimilarMovieContentsAdapter.SimilarContentViewHolder>(),
    ContentItemCallback<Movie> {

    @Inject
    lateinit var assistedFactory: MovieDetailViewModelAssistedFactory

    private val viewModel: MovieDetailViewModel by viewModels {
        val movie = arguments?.getParcelable<Movie>(PARAMS_MOVIES)
        val id = movie?.id ?: -1
        MovieDetailViewModel.Factory(assistedFactory, id)
    }

    override fun initAdapter(): ListAdapter<Movie, SimilarMovieContentsAdapter.SimilarContentViewHolder> =
        SimilarMovieContentsAdapter().apply {
            setItemCallback(this@MovieContentFragment)
        }

    override fun initUI() {
        val currentMovie = arguments?.getParcelable<Movie>(PARAMS_MOVIES)

        with(binding) {
            ivViewTrailerDetail.setOnClickListener { }
            btnWatchDetail.setOnClickListener { }

            toggleBtnFav.setOnClickListener {
                if (!toggleBtnFav.isChecked) {
                    // delete
                    viewModel.removeFavMovie(currentMovie)
                    showToast(getString(R.string.favorites_deleted_tv_show_message))
                } else {
                    // add
                    viewModel.addFavMovie(currentMovie)
                    showToast(getString(R.string.favorites_added_tv_show_message))
                }
            }
        }
    }

    override fun observeData() {
        with(viewModel) {
            getDetailMovie().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    StatusRes.SUCCESS -> setupSuccessUI(resource.data)
                    StatusRes.LOADING -> setupLoadingUI()
                    StatusRes.ERROR -> setErrorUI()
                    else -> Unit
                }
            }

            isFavorite.observe(viewLifecycleOwner) { movie ->
                binding.toggleBtnFav.isChecked = movie != null
            }

            getSimilarMovies().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    StatusRes.SUCCESS -> initRecyclerData(resource.data)
                    StatusRes.ERROR -> setNoResultsUI(true)
                    StatusRes.LOADING -> setLoadingSimilarContents(true)
                    else -> Unit
                }
            }
        }
    }

    override fun setupSuccessUI(data: Movie?) {
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
                    .load(ApiEndpoint.IMAGE_URL + data.backdropPath)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.bg_poster_error)
                    .placeholder(R.drawable.bg_poster_placeholder)
                    .into(ivPosterBgDetail)

                Glide.with(root)
                    .load(ApiEndpoint.IMAGE_URL + data.posterPath)
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

    override fun onClick(item: Movie) {
        with(Intent(requireActivity(), DetailActivity::class.java)) {
            putExtra(DetailActivity.EXTRA_CONTENT_MOVIE, item)
            putExtra(DetailActivity.GOTO_MOVIE_DETAIL, true)

            requireActivity().startActivity(this)
        }
    }

    companion object {
        val TAG: String = MovieContentFragment::class.java.simpleName

        fun newInstance(movie: Movie): MovieContentFragment =
            MovieContentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAMS_MOVIES, movie)
                }
            }
    }
}