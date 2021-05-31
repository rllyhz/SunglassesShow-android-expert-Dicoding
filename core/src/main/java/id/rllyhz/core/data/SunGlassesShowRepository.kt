package id.rllyhz.core.data

import androidx.paging.DataSource
import id.rllyhz.core.data.local.LocalDataSource
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.data.remote.RemoteDataSource
import id.rllyhz.core.domain.model.Movie
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.core.domain.repository.ISunGlassesShowRepository
import id.rllyhz.core.utils.ext.asEntity
import id.rllyhz.core.utils.ext.asModel
import id.rllyhz.core.vo.ApiResponse
import id.rllyhz.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SunGlassesShowRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : ISunGlassesShowRepository {
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        object : NetWorkBoundResource<List<Movie>, List<Movie>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                local.getMovies().map { it.asModel() }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<Movie>>> =
                remote.getAllMovies()

            override suspend fun saveCallResult(data: List<Movie>) =
                local.addMovies(data.asEntity())
        }.asFlow()

    override fun getTVShows(): Flow<Resource<List<TVShow>>> =
        object : NetWorkBoundResource<List<TVShow>, List<TVShow>>() {
            override fun loadFromDB(): Flow<List<TVShow>> =
                local.getTVShows().map { it.asModel() }

            override fun shouldFetch(data: List<TVShow>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TVShow>>> =
                remote.getAllTVShows()

            override suspend fun saveCallResult(data: List<TVShow>) =
                local.addTVShows(data.asEntity())
        }.asFlow()

    override fun getMovieById(id: Int): Flow<Resource<Movie>> =
        object : NetWorkBoundResource<Movie, Movie>() {
            override fun loadFromDB(): Flow<Movie> =
                local.getMovieById(id).map { it.asModel() }

            override fun shouldFetch(data: Movie?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<Movie>> =
                remote.getMovieById(id)

            override suspend fun saveCallResult(data: Movie) =
                local.addMovie(data.asEntity())
        }.asFlow()

    override fun getTVShowById(id: Int): Flow<Resource<TVShow>> =
        object : NetWorkBoundResource<TVShow, TVShow>() {
            override fun loadFromDB(): Flow<TVShow> =
                local.getTVShowById(id).map { it.asModel() }

            override fun shouldFetch(data: TVShow?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<TVShow>> =
                remote.getTVShowById(id)

            override suspend fun saveCallResult(data: TVShow) =
                local.addTVShow(data.asEntity())
        }.asFlow()

    override fun getSimilarMoviesOf(id: Int): Flow<Resource<List<Movie>>> =
        remote.getSimilarMoviesOfMovie(id)

    override fun getSimilarTVShowsOf(id: Int): Flow<Resource<List<TVShow>>> =
        remote.getSimilarTVShowsOfMovie(id)

    override fun searchMovies(query: String): Flow<Resource<List<Movie>>> =
        remote.searchMovies(query)

    override fun searchTVShows(query: String): Flow<Resource<List<TVShow>>> =
        remote.searchTVShows(query)

    override fun getFavMovies(): DataSource.Factory<Int, FavMovie> =
        local.getFavMovies()

    override fun getFavTVShows(): DataSource.Factory<Int, FavTVShow> =
        local.getFavTVShows()

    override fun getFavMovieById(id: Int): Flow<FavMovie?> =
        local.getFavMovieById(id)

    override fun getFavTVShowById(id: Int): Flow<FavTVShow?> =
        local.getFavTVShowById(id)

    override suspend fun addFavMovie(favMovie: FavMovie): Long =
        local.addFavMovie(favMovie)

    override suspend fun addFavTVShow(favTVShow: FavTVShow): Long =
        local.addFavTVShow(favTVShow)

    override suspend fun deleteFavMovie(favMovie: FavMovie): Int =
        local.deleteFavMovie(favMovie)

    override suspend fun deleteFavTVShow(favTVShow: FavTVShow): Int =
        local.deleteFavTVShow(favTVShow)
}