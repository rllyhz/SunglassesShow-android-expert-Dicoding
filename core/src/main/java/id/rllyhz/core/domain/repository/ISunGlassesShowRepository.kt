package id.rllyhz.core.domain.repository

import androidx.paging.DataSource
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.domain.model.Movie
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ISunGlassesShowRepository {
    fun getMovies(): Flow<Resource<List<Movie>>>

    fun getTVShows(): Flow<Resource<List<TVShow>>>

    fun getMovieById(id: Int): Flow<Resource<Movie>>

    fun getTVShowById(id: Int): Flow<Resource<TVShow>>

    fun getSimilarMoviesOf(id: Int): Flow<Resource<List<Movie>>>

    fun getSimilarTVShowsOf(id: Int): Flow<Resource<List<TVShow>>>

    fun searchMovies(query: String): Flow<Resource<List<Movie>>>

    fun searchTVShows(query: String): Flow<Resource<List<TVShow>>>

    fun getFavMovies(): DataSource.Factory<Int, FavMovie>

    fun getFavTVShows(): DataSource.Factory<Int, FavTVShow>

    fun getFavMovieById(id: Int): Flow<FavMovie?>

    fun getFavTVShowById(id: Int): Flow<FavTVShow?>

    suspend fun addFavMovie(favMovie: FavMovie): Long

    suspend fun addFavTVShow(favTVShow: FavTVShow): Long

    suspend fun deleteFavMovie(favMovie: FavMovie): Int

    suspend fun deleteFavTVShow(favTVShow: FavTVShow): Int
}