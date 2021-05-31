package id.rllyhz.favoritecontent.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import id.rllyhz.favoritecontent.adapter.FavoritesPagerAdapter
import id.rllyhz.sunglassesshow.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private var pagerAdapter: FavoritesPagerAdapter? = null
    private var mediator: TabLayoutMediator? = null
    private var _activity: AppCompatActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setInitialUI()
    }

    private fun setInitialUI() {
        _activity?.supportActionBar?.elevation = 0f

        pagerAdapter = FavoritesPagerAdapter(
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )

        with(binding) {
            viewPagerFavorites.adapter = pagerAdapter

            mediator = TabLayoutMediator(
                tabLayoutFavorites,
                viewPagerFavorites
            ) { tab, position ->
                tab.text = getString(FavoritesPagerAdapter.FAV_TAB_TITLES[position])
            }

            mediator?.attach()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _activity = context as AppCompatActivity
    }

    override fun onDetach() {
        super.onDetach()
        _activity = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pagerAdapter = null
        mediator?.detach()
        mediator = null
        binding.viewPagerFavorites.adapter = null
        _binding = null
    }
}