package id.rllyhz.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.domain.model.Movie
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface SunGlassesShowUseCase {
    fun getMovies(): Flow<Resource<List<Movie>>>

    fun getTVShows(): Flow<Resource<List<TVShow>>>

    fun getMovieById(id: Int): LiveData<Resource<Movie>>

    fun getTVShowById(id: Int): LiveData<Resource<TVShow>>

    fun getSimilarMoviesOf(id: Int): LiveData<Resource<List<Movie>>>

    fun getSimilarTVShowsOf(id: Int): LiveData<Resource<List<TVShow>>>

    fun searchMovies(query: String): Flow<Resource<List<Movie>>>

    fun searchTVShows(query: String): Flow<Resource<List<TVShow>>>

    fun getFavMovies(): LiveData<PagedList<FavMovie>>

    fun getFavTVShows(): LiveData<PagedList<FavTVShow>>

    fun getFavMovieById(id: Int): Flow<FavMovie?>

    fun getFavTVShowById(id: Int): Flow<FavTVShow?>

    suspend fun addFavMovie(favMovie: FavMovie): Long

    suspend fun addFavTVShow(favTVShow: FavTVShow): Long

    suspend fun deleteFavMovie(favMovie: FavMovie): Int

    suspend fun deleteFavTVShow(favTVShow: FavTVShow): Int
}