package id.rllyhz.favoritecontent.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.domain.usecase.SunGlassesShowUseCase
import id.rllyhz.core.utils.ext.asModel
import id.rllyhz.favoritecontent.adapter.FavTVShowsAdapter
import id.rllyhz.favoritecontent.utils.FavItemCallback
import id.rllyhz.favoritecontent.utils.SwipeItemCallback
import id.rllyhz.favoritecontent.utils.ViewModelFactory
import id.rllyhz.favoritecontent.viewmodel.FavoritesViewModel
import id.rllyhz.sunglassesshow.databinding.FragmentFavTvShowsBinding
import id.rllyhz.sunglassesshow.detailcontent.DetailActivity
import id.rllyhz.sunglassesshow.main.MainActivity

class FavTVShowsFragment : Fragment(), FavItemCallback<FavTVShow> {
    private var _binding: FragmentFavTvShowsBinding? = null
    private val binding get() = _binding!!

    private var favTVShowsAdapter: FavTVShowsAdapter? = null
    private lateinit var viewModel: FavoritesViewModel

    private var useCase: SunGlassesShowUseCase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(useCase)
        )[FavoritesViewModel::class.java]

        setInitialUI()
        observeData()
    }

    private fun showView(view: View, isShowing: Boolean) {
        view.visibility = if (isShowing) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setInitialUI() {
        favTVShowsAdapter = FavTVShowsAdapter()
        favTVShowsAdapter?.setItemCallback(this)

        with(binding) {
            val swipedItemCallback = object : SwipeItemCallback(ItemTouchHelper.LEFT) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val favTvShow = favTVShowsAdapter?.get(viewHolder.bindingAdapterPosition)
                    viewModel.deleteFavTVShow(favTvShow)
                    favTVShowsAdapter?.currentList?.dataSource?.invalidate()
                }
            }

            with(rvFavTvShows) {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = favTVShowsAdapter

                ItemTouchHelper(swipedItemCallback).attachToRecyclerView(this)
            }
        }
    }

    private fun observeData() {
        viewModel.favTVShows?.observe(viewLifecycleOwner) {
            with(binding) {
                if (it.size != 0) {
                    showView(tvFeedback, false)
                    showView(rvFavTvShows, true)
                    favTVShowsAdapter?.submitList(it)
                } else {
                    showView(tvFeedback, true)
                    showView(rvFavTvShows, false)
                }
            }
        }
    }

    override fun onClick(item: FavTVShow) {
        with(Intent(requireActivity(), DetailActivity::class.java)) {
            putExtra(DetailActivity.EXTRA_CONTENT_TV_SHOW, item.asModel())
            putExtra(DetailActivity.GOTO_TV_SHOW_DETAIL, true)

            requireActivity().startActivity(this)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Hilt is not supported in dynamic feature yet
        useCase = (context as MainActivity).useCase
    }

    override fun onDetach() {
        super.onDetach()
        useCase = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        favTVShowsAdapter = null
    }

    companion object {
        fun newInstance(): FavTVShowsFragment = FavTVShowsFragment()
    }
}