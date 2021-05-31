package id.rllyhz.favoritecontent.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.domain.usecase.SunGlassesShowUseCase
import id.rllyhz.core.utils.ext.asModel
import id.rllyhz.favoritecontent.adapter.FavMoviesAdapter
import id.rllyhz.favoritecontent.utils.FavItemCallback
import id.rllyhz.favoritecontent.utils.SwipeItemCallback
import id.rllyhz.favoritecontent.utils.ViewModelFactory
import id.rllyhz.favoritecontent.viewmodel.FavoritesViewModel
import id.rllyhz.sunglassesshow.databinding.FragmentFavMoviesBinding
import id.rllyhz.sunglassesshow.detailcontent.DetailActivity
import id.rllyhz.sunglassesshow.main.MainActivity

class FavMoviesFragment : Fragment(), FavItemCallback<FavMovie> {
    private var _binding: FragmentFavMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var favMoviesAdapter: FavMoviesAdapter
    private lateinit var viewModel: FavoritesViewModel

    private var useCase: SunGlassesShowUseCase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavMoviesBinding.inflate(inflater, container, false)
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

    private fun setInitialUI() {
        favMoviesAdapter = FavMoviesAdapter()
        favMoviesAdapter.setItemCallback(this)

        with(binding) {
            val swipedItemCallback = object : SwipeItemCallback(ItemTouchHelper.RIGHT) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val favMovie = favMoviesAdapter.get(viewHolder.bindingAdapterPosition)
                    viewModel.deleteFavMovie(favMovie)
                    favMoviesAdapter.currentList?.dataSource?.invalidate()
                }
            }

            with(rvFavMovies) {
                setHasFixedSize(true)
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = favMoviesAdapter

                ItemTouchHelper(swipedItemCallback).attachToRecyclerView(this)
            }
        }
    }

    private fun observeData() {
        viewModel.favMovies?.observe(viewLifecycleOwner) {
            with(binding) {
                if (it.size != 0) {
                    showView(tvFeedback, false)
                    showView(rvFavMovies, true)
                    favMoviesAdapter.submitList(it)
                    Log.d("Hehe", "setup")
                    Log.d("Hehe", it.toString())
                } else {
                    showView(tvFeedback, true)
                    showView(rvFavMovies, false)
                }
            }
        }
    }

    private fun showView(view: View, isShowing: Boolean) {
        view.visibility = if (isShowing) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onClick(item: FavMovie) {
        with(Intent(requireActivity(), DetailActivity::class.java)) {
            putExtra(DetailActivity.EXTRA_CONTENT_MOVIE, item.asModel())
            putExtra(DetailActivity.GOTO_MOVIE_DETAIL, true)

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
    }

    companion object {
        fun newInstance(): FavMoviesFragment =
            FavMoviesFragment()
    }
}