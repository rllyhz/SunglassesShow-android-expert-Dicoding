package id.rllyhz.sunglassesshow.utils

import dagger.assisted.AssistedFactory
import id.rllyhz.sunglassesshow.detailcontent.movie.MovieDetailViewModel

@AssistedFactory
interface MovieDetailViewModelAssistedFactory {
    fun create(id: Int): MovieDetailViewModel
}