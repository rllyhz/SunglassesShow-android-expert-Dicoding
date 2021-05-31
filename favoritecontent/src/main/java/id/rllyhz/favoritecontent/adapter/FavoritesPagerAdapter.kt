package id.rllyhz.favoritecontent.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.rllyhz.favoritecontent.R
import id.rllyhz.favoritecontent.ui.FavMoviesFragment
import id.rllyhz.favoritecontent.ui.FavTVShowsFragment

class FavoritesPagerAdapter(
    fm: FragmentManager,
    lifeCycle: Lifecycle
) : FragmentStateAdapter(fm, lifeCycle) {

    override fun getItemCount(): Int =
        FAV_TAB_TITLES.size

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavMoviesFragment.newInstance()
            1 -> FavTVShowsFragment.newInstance()
            else -> FavMoviesFragment.newInstance()
        }

    companion object {
        val FAV_TAB_TITLES =
            intArrayOf(R.string.favorites_movie, R.string.favorites_tv_show)
    }
}