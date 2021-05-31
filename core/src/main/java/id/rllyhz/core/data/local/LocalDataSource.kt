package id.rllyhz.core.data.local

import androidx.paging.DataSource
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.data.local.entity.MovieEntity
import id.rllyhz.core.data.local.entity.TVShowEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: SunGlassesShowDao
) {
    fun getMovies(): Flow<List<MovieEntity>> = dao.getMovies()

    fun getTVShows(): Flow<List<TVShowEntity>> = dao.getTVShows()

    suspend fun addMovies(movies: List<MovieEntity>) = dao.addMovies(movies)

    suspend fun addTVShows(tvShows: List<TVShowEntity>) = dao.addTVShows(tvShows)

    suspend fun addMovie(movie: MovieEntity) = dao.addMovie(movie)

    suspend fun addTVShow(tvShow: TVShowEntity) = dao.addTVShow(tvShow)

    fun getMovieById(id: Int): Flow<MovieEntity> = dao.getMovieById(id)

    fun getTVShowById(id: Int): Flow<TVShowEntity> = dao.getTVShowById(id)

    fun getFavMovies(): DataSource.Factory<Int, FavMovie> = dao.getFavMovies()

    fun getFavTVShows(): DataSource.Factory<Int, FavTVShow> = dao.getFavTVShows()

    fun getFavMovieById(id: Int): Flow<FavMovie?> = dao.getFavMovieById(id)

    fun getFavTVShowById(id: Int): Flow<FavTVShow?> = dao.getFavTVShowById(id)

    suspend fun addFavMovie(favMovie: FavMovie): Long = dao.addFavMovie(favMovie)

    suspend fun addFavTVShow(favTVShow: FavTVShow): Long = dao.addFavTVShow(favTVShow)

    suspend fun deleteFavTVShow(favTVShow: FavTVShow): Int = dao.deleteFavTVShow(favTVShow)

    suspend fun deleteFavMovie(favMovie: FavMovie): Int = dao.deleteFavMovie(favMovie)
}