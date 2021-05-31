package id.rllyhz.favoritecontent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.domain.usecase.SunGlassesShowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val useCase: SunGlassesShowUseCase?
) : ViewModel() {
    val favMovies = useCase?.getFavMovies()
    val favTVShows = useCase?.getFavTVShows()

    fun deleteFavMovie(favMovie: FavMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase?.deleteFavMovie(favMovie)
        }
    }

    fun deleteFavTVShow(favTVShow: FavTVShow) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase?.deleteFavTVShow(favTVShow)
        }
    }
}