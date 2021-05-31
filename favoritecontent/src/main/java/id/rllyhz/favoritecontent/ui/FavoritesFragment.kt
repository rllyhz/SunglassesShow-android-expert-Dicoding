package id.rllyhz.favoritecontent.ui

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
        (requireActivity() as AppCompatActivity).let { _activity ->
            _activity.supportActionBar?.elevation = 0f

            val pagerAdapter = FavoritesPagerAdapter(
                _activity.supportFragmentManager,
                _activity.lifecycle
            )

            with(binding) {
                viewPagerFavorites.adapter = pagerAdapter
                TabLayoutMediator(
                    tabLayoutFavorites,
                    viewPagerFavorites
                ) { tab, position ->
                    tab.text = getString(FavoritesPagerAdapter.FAV_TAB_TITLES[position])
                }.attach()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}