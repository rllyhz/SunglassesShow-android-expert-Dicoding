package id.rllyhz.sunglassesshow.detailcontent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.sunglassesshow.databinding.FragmentContentBinding

abstract class ContentFragment<T, VH : RecyclerView.ViewHolder> : Fragment() {
    private var _binding: FragmentContentBinding? = null
    protected val binding get() = _binding!! // only valid between onCreateView and onDestroyView.

    private var _adapter: ListAdapter<T, VH>? = null
    protected var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupInitialUI()
        observeData()
    }

    protected abstract fun initAdapter(): ListAdapter<T, VH>

    private fun setupInitialUI() {
        _adapter = initAdapter()

        with(binding.rvSimilarContentDetail) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = _adapter
        }

        initUI()
    }

    protected abstract fun initUI()

    protected fun initRecyclerData(data: List<T>?) {
        if (data == null || data.isEmpty())
            setNoResultsUI(true)
        else {
            _adapter?.submitList(data)
            setNoResultsUI(false)
        }

        setLoadingSimilarContents(false)
    }

    protected abstract fun observeData()

    protected abstract fun setupSuccessUI(data: T?)

    protected fun showUI(isShowing: Boolean) {
        with(binding) {
            showView(llViewTrailer, isShowing)
            showView(ivPosterBgDetail, isShowing)
            showView(ivPosterDetail, isShowing)
            showView(tvTitleDetail, isShowing)
            showView(tvDurationDetail, isShowing)
            showView(tvGenresDetail, isShowing)
            showView(tvNoSimilarText, !isShowing)
            showView(tvOverviewLabelDetail, isShowing)
            showView(tvRateDetail, isShowing)
            showView(tvReleasedAtDetail, isShowing)
            showView(tvSimilarContentLabelDetail, isShowing)
            showView(tvStatusDetail, isShowing)
            showView(tvSynopsisDetail, isShowing)
            showView(btnWatchDetail, isShowing)
            showView(toggleBtnFav, isShowing)
            showView(rbDetail, isShowing)
            showView(rvSimilarContentDetail, isShowing)

            showView(includeLoading.root, !isShowing)
            showView(includeError.root, !isShowing)
        }
    }

    protected fun setupLoadingUI() {
        isLoading = true

        with(binding) {
            showUI(false)
            showView(tvNoSimilarText, false)
            showView(includeError.root, false)
            setLoadingSimilarContents(false)
            with(includeLoading.root) {
                showView(this, true)
                showShimmer(true)
            }
        }
    }

    protected fun setErrorUI() {
        isLoading = false

        with(binding) {
            showUI(false)
            showView(tvNoSimilarText, false)
            setLoadingSimilarContents(false)

            with(includeLoading.root) {
                showShimmer(false)
                stopShimmer()
                showView(this, false)
            }

            showView(includeError.root, true)
        }
    }

    protected fun setNoResultsUI(state: Boolean) {
        showView(binding.tvNoSimilarText, state)
        setLoadingSimilarContents(false)
    }

    protected fun setLoadingSimilarContents(state: Boolean) {
        with(binding.progressbarSimilarContents) {
            if (isLoading)
                showView(this, false)
            else
                showView(this, state)
        }
    }

    private fun showView(view: View, isShowing: Boolean) {
        view.visibility = if (isShowing) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoiding memory leaks
        _adapter = null
    }

    companion object {
        const val PARAMS_TV_SHOWS = "PARAMS_TV_SHOWS"
        const val PARAMS_MOVIES = "PARAMS_MOVIES"
    }
}