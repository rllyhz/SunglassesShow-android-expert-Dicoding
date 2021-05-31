package id.rllyhz.sunglassesshow.utils

import dagger.assisted.AssistedFactory
import id.rllyhz.sunglassesshow.detailcontent.tvshow.TVShowDetailViewModel

@AssistedFactory
interface TVShowDetailViewModelAssistedFactory {
    fun create(id: Int): TVShowDetailViewModel
}