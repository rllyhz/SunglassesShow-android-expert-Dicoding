package id.rllyhz.sunglassesshow.tvshows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.core.vo.StatusRes
import id.rllyhz.sunglassesshow.databinding.FragmentTvShowsBinding
import id.rllyhz.sunglassesshow.detailcontent.DetailActivity
import id.rllyhz.sunglassesshow.main.MainViewModel
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
@FlowPreview
class TVShowsFragment : Fragment(), TVShowsAdapter.TVShowItemCallback {
    private var _binding: FragmentTvShowsBinding? = null
    private val binding get() = _binding!!

    private var tvShowsAdapter: TVShowsAdapter? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialUI()
        observeData()
    }

    private fun setInitialUI() {
        tvShowsAdapter = TVShowsAdapter()
        tvShowsAdapter?.setItemCallback(this)

        with(binding) {

            with(searchViewTvShows) {
                imeOptions = EditorInfo.IME_ACTION_DONE

                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean =
                        false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText != null) {
                            viewModel.setQueryForSearchingTVShows(newText)
                            return true
                        }

                        return false
                    }
                })
            }

            with(rvTvShows) {
                adapter = tvShowsAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
                setHasFixedSize(true)
            }
        }
    }

    private fun observeData() {
        viewModel.tvShows.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                StatusRes.LOADING -> setLoadingUI()
                StatusRes.ERROR -> setErrorUI()
                StatusRes.SUCCESS -> setSuccessUI(resource.data)
                else -> Unit
            }
        }

        viewModel.getSearchingTVShowsResults().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                StatusRes.LOADING -> setLoadingWhileSearchingUI()
                StatusRes.ERROR -> setNoResultsUI()
                StatusRes.SUCCESS -> setSuccessUI(resource.data)
                else -> Unit
            }
        }
    }

    private fun setSuccessUI(data: List<TVShow>?) {
        if (data != null && data.isNotEmpty()) {
            tvShowsAdapter?.submitList(data)

            with(binding) {
                showView(searchViewTvShows, true)
                showView(rvTvShows, true)
                showView(progressbar, false)

                with(includeLoading.root) {
                    stopShimmer()
                    showView(this, false)
                    showShimmer(false)
                }
            }
        } else
            setNoResultsUI()
    }

    private fun setLoadingUI() {
        with(binding) {
            showView(searchViewTvShows, false)
            showView(rvTvShows, false)
            showView(progressbar, false)

            with(includeLoading.root) {
                showView(this, true)
                showShimmer(true)
                startShimmer()
            }
        }
    }

    private fun setErrorUI() {
        with(binding) {
            showView(searchViewTvShows, false)
            showView(rvTvShows, false)
            showView(progressbar, false)

            with(includeLoading.root) {
                stopShimmer()
                showView(this, false)
                showShimmer(false)
            }

            with(includeError.root) {
                showView(this, true)
            }
        }
    }

    private fun setLoadingWhileSearchingUI() {
        with(binding) {
            showView(rvTvShows, false)
            showView(tvSearchingFeedback, false)
            showView(progressbar, true)
        }
    }

    private fun setNoResultsUI() {
        with(binding) {
            showView(rvTvShows, false)
            showView(tvSearchingFeedback, true)
            showView(progressbar, false)
        }
    }

    private fun showView(view: View, isShowing: Boolean) {
        view.visibility = if (isShowing) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onClick(tvShow: TVShow) {
        with(Intent(requireActivity(), DetailActivity::class.java)) {
            putExtra(DetailActivity.EXTRA_CONTENT_TV_SHOW, tvShow)
            putExtra(DetailActivity.GOTO_TV_SHOW_DETAIL, true)

            requireActivity().startActivity(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoiding memory leaks
        tvShowsAdapter = null
    }
}