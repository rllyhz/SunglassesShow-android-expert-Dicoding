package id.rllyhz.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.domain.model.Movie
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.core.domain.repository.ISunGlassesShowRepository
import id.rllyhz.core.vo.Resource
import kotlinx.coroutines.flow.Flow

class SunGlassesShowInteractor(
    private val repository: ISunGlassesShowRepository,
    private val pagedListConfig: PagedList.Config
) : SunGlassesShowUseCase {
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        repository.getMovies()

    override fun getTVShows(): Flow<Resource<List<TVShow>>> =
        repository.getTVShows()

    override fun getMovieById(id: Int): LiveData<Resource<Movie>> =
        repository.getMovieById(id).asLiveData()

    override fun getTVShowById(id: Int): LiveData<Resource<TVShow>> =
        repository.getTVShowById(id).asLiveData()

    override fun getSimilarMoviesOf(id: Int): LiveData<Resource<List<Movie>>> =
        repository.getSimilarMoviesOf(id).asLiveData()

    override fun getSimilarTVShowsOf(id: Int): LiveData<Resource<List<TVShow>>> =
        repository.getSimilarTVShowsOf(id).asLiveData()

    override fun searchMovies(query: String): Flow<Resource<List<Movie>>> =
        repository.searchMovies(query)

    override fun searchTVShows(query: String): Flow<Resource<List<TVShow>>> =
        repository.searchTVShows(query)

    override fun getFavMovies(): LiveData<PagedList<FavMovie>> =
        LivePagedListBuilder(repository.getFavMovies(), pagedListConfig).build()

    override fun getFavTVShows(): LiveData<PagedList<FavTVShow>> =
        LivePagedListBuilder(repository.getFavTVShows(), pagedListConfig).build()

    override fun getFavMovieById(id: Int): Flow<FavMovie?> =
        repository.getFavMovieById(id)

    override fun getFavTVShowById(id: Int): Flow<FavTVShow?> =
        repository.getFavTVShowById(id)

    override suspend fun addFavMovie(favMovie: FavMovie): Long =
        repository.addFavMovie(favMovie)

    override suspend fun addFavTVShow(favTVShow: FavTVShow): Long =
        repository.addFavTVShow(favTVShow)

    override suspend fun deleteFavMovie(favMovie: FavMovie): Int =
        repository.deleteFavMovie(favMovie)

    override suspend fun deleteFavTVShow(favTVShow: FavTVShow): Int =
        repository.deleteFavTVShow(favTVShow)
}