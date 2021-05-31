package id.rllyhz.sunglassesshow.detailcontent.tvshow

import androidx.lifecycle.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.core.domain.usecase.SunGlassesShowUseCase
import id.rllyhz.core.utils.ext.asFavModel
import id.rllyhz.core.vo.Resource
import id.rllyhz.sunglassesshow.utils.TVShowDetailViewModelAssistedFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TVShowDetailViewModel @AssistedInject constructor(
    private val useCase: SunGlassesShowUseCase,
    @Assisted private val id: Int
) : ViewModel() {

    class Factory(
        private val assistedFactory: TVShowDetailViewModelAssistedFactory,
        private val id: Int,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return assistedFactory.create(id) as T
        }
    }

    private lateinit var detailTVShow: LiveData<Resource<TVShow>>
    private lateinit var similarTVShows: LiveData<Resource<List<TVShow>>>
    private var isFavoriteTrigger = MutableLiveData(false)

    val isFavorite = Transformations.switchMap(isFavoriteTrigger) {
        useCase.getFavTVShowById(id)
    }

    init {
        initAllRequiredData()
    }

    fun initAllRequiredData() = viewModelScope.launch {
        detailTVShow = useCase.getTVShowById(id)
        similarTVShows = useCase.getSimilarTVShowsOf(id)
    }

    fun getDetailTVShow() = detailTVShow
    fun getSimilarTVShows() = similarTVShows

    fun addFavTVShow(tvShow: TVShow?) = viewModelScope.launch(Dispatchers.IO) {
        if (tvShow != null) {
            useCase.addFavTVShow(tvShow.asFavModel())
        }
    }

    fun removeFavTVShow(tvShow: TVShow?) = viewModelScope.launch(Dispatchers.IO) {
        if (tvShow != null) {
            useCase.deleteFavTVShow(tvShow.asFavModel())
        }
    }
}