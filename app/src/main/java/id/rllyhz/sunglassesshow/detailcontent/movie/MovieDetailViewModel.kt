package id.rllyhz.sunglassesshow.detailcontent.movie

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import id.rllyhz.core.domain.model.Movie
import id.rllyhz.core.domain.usecase.SunGlassesShowUseCase
import id.rllyhz.core.utils.ext.asFavModel
import id.rllyhz.core.vo.Resource
import id.rllyhz.sunglassesshow.utils.MovieDetailViewModelAssistedFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel @AssistedInject constructor(
    private val useCase: SunGlassesShowUseCase,
    @Assisted private val id: Int
) : ViewModel() {

    class Factory(
        private val assistedFactory: MovieDetailViewModelAssistedFactory,
        private val id: Int,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(id) as T
        }
    }

    private lateinit var detailMovie: LiveData<Resource<Movie>>
    private lateinit var similarMovies: LiveData<Resource<List<Movie>>>
    private var isFavoriteTrigger = MutableLiveData(false)
    val isFavorite = Transformations.switchMap(isFavoriteTrigger) {
        useCase.getFavMovieById(id).asLiveData()
    }

    init {
        initAllRequiredData()
    }

    private fun initAllRequiredData() = viewModelScope.launch {
        detailMovie = useCase.getMovieById(id)
        similarMovies = useCase.getSimilarMoviesOf(id)
    }

    fun getDetailMovie() = detailMovie
    fun getSimilarMovies() = similarMovies

    fun addFavMovie(movie: Movie?) = viewModelScope.launch(Dispatchers.IO) {
        if (movie != null) {
            useCase.addFavMovie(movie.asFavModel())
        }
    }

    fun removeFavMovie(movie: Movie?) = viewModelScope.launch(Dispatchers.IO) {
        if (movie != null) {
            useCase.deleteFavMovie(movie.asFavModel())
        }
    }
}