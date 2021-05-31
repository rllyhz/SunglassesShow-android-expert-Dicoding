package id.rllyhz.sunglassesshow.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.rllyhz.core.domain.model.Movie
import id.rllyhz.core.vo.StatusRes
import id.rllyhz.sunglassesshow.databinding.FragmentMoviesBinding
import id.rllyhz.sunglassesshow.detailcontent.DetailActivity
import id.rllyhz.sunglassesshow.main.MainViewModel
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
@FlowPreview
class MoviesFragment : Fragment(), MoviesAdapter.MovieItemCallback {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!! // only valid between onCreateView and onDestroyView

    private var moviesAdapter: MoviesAdapter? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialUI()
        observeData()
    }

    private fun setInitialUI() {
        moviesAdapter = MoviesAdapter()
        moviesAdapter?.setItemCallback(this)

        with(binding) {

            with(searchViewMovies) {
                imeOptions = EditorInfo.IME_ACTION_DONE

                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean =
                        false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText != null) {
                            viewModel.setQueryForSearchingMovies(newText)
                            return true
                        }

                        return false
                    }
                })
            }

            with(rvMovies) {
                adapter = moviesAdapter
                layoutManager = GridLayoutManager(requireContext(), 2)
                setHasFixedSize(true)
            }
        }
    }

    private fun observeData() {
        viewModel.movies.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                StatusRes.LOADING -> setLoadingUI()
                StatusRes.ERROR -> setErrorUI()
                StatusRes.SUCCESS -> setSuccessUI(resource.data)
                else -> Unit
            }
        }

        viewModel.getSearchingMoviesResults().observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                StatusRes.LOADING -> setLoadingWhileSearchingUI()
                StatusRes.ERROR -> setNoResultsUI()
                StatusRes.SUCCESS -> setSuccessUI(resource.data)
                else -> Unit
            }
        }
    }

    private fun setSuccessUI(data: List<Movie>?) {
        if (data != null && data.isNotEmpty()) {
            moviesAdapter?.submitList(data)

            with(binding) {
                showView(searchViewMovies, true)
                showView(rvMovies, true)
                showView(tvSearchingFeedback, false)
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
            showView(searchViewMovies, false)
            showView(rvMovies, false)
            showView(tvSearchingFeedback, false)
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
            showView(searchViewMovies, false)
            showView(rvMovies, false)
            showView(tvSearchingFeedback, false)
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
            showView(rvMovies, false)
            showView(tvSearchingFeedback, false)
            showView(progressbar, true)
        }
    }

    private fun setNoResultsUI() {
        with(binding) {
            showView(rvMovies, false)
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

    override fun onClick(movie: Movie) {
        with(Intent(requireActivity(), DetailActivity::class.java)) {
            putExtra(DetailActivity.EXTRA_CONTENT_MOVIE, movie)
            putExtra(DetailActivity.GOTO_MOVIE_DETAIL, true)

            requireActivity().startActivity(this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoiding memory leaks
        moviesAdapter = null
    }
}