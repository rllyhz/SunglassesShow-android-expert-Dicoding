package id.rllyhz.core.data.local

import androidx.paging.DataSource
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: SunGlassesShowDao
) {
    fun getFavMovies(): DataSource.Factory<Int, FavMovie> = dao.getFavMovies()

    fun getFavTVShows(): DataSource.Factory<Int, FavTVShow> = dao.getFavTVShows()

    fun getFavMovieById(id: Int): Flow<FavMovie?> = dao.getFavMovieById(id)

    fun getFavTVShowById(id: Int): Flow<FavTVShow?> = dao.getFavTVShowById(id)

    suspend fun addFavMovie(favMovie: FavMovie): Long = dao.addFavMovie(favMovie)

    suspend fun addFavTVShow(favTVShow: FavTVShow): Long = dao.addFavTVShow(favTVShow)

    suspend fun deleteFavTVShow(favTVShow: FavTVShow): Int = dao.deleteFavTVShow(favTVShow)

    suspend fun deleteFavMovie(favMovie: FavMovie): Int = dao.deleteFavMovie(favMovie)
}