package id.rllyhz.favoritecontent.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.rllyhz.core.domain.usecase.SunGlassesShowUseCase
import id.rllyhz.favoritecontent.viewmodel.FavoritesViewModel

class ViewModelFactory(
    private val useCase: SunGlassesShowUseCase?
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) ->
                FavoritesViewModel(useCase) as T
            else -> throw Throwable("ViewModel not found: " + modelClass.name)
        }
}