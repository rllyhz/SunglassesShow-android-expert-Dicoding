package id.rllyhz.core.data.remote

import id.rllyhz.core.api.ApiEndpoint
import id.rllyhz.core.utils.ext.asModels
import id.rllyhz.core.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: ApiEndpoint
) {

    fun getAllMovies() = flow {
        emit(Resource.loading(null))

        try {
            val callResults = api.discoverMovies()
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.movies.asModels()))
            } else {
                emit(Resource.error(null, callResults.message()))
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
        }
    }.flowOn(Dispatchers.IO)

    fun getAllTVShows() = flow {
        emit(Resource.loading(null))

        try {
            val callResults = api.discoverTVShows()
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.tvShows.asModels()))
            } else {
                emit(Resource.error(null, callResults.message()))
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
        }
    }.flowOn(Dispatchers.IO)

    fun getMovieById(id: Int) = flow {
        emit(Resource.loading(null))

        try {
            val callResults = api.getMovieDetailOf(id)
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.asModels()))
            } else {
                emit(Resource.error(null, callResults.message()))
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
        }
    }.flowOn(Dispatchers.IO)

    fun getTVShowById(id: Int) = flow {
        emit(Resource.loading(null))

        try {
            val callResults = api.getTvShowDetailOf(id)
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.asModels()))
            } else {
                emit(Resource.error(null, callResults.message()))
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
        }
    }.flowOn(Dispatchers.IO)

    fun getSimilarMoviesOfMovie(id: Int) = flow {
        emit(Resource.loading(null))

        try {
            val callResults = api.getSimilarMoviesOf(id)
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.movies.asModels()))
            } else {
                emit(Resource.error(null, callResults.message()))
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
        }
    }.flowOn(Dispatchers.IO)

    fun getSimilarTVShowsOfMovie(id: Int) = flow {
        emit(Resource.loading(null))

        try {
            val callResults = api.getSimilarTVShowsOf(id)
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.tvShows.asModels()))
            } else {
                emit(Resource.error(null, callResults.message()))
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
        }
    }.flowOn(Dispatchers.IO)

    fun searchMovies(query: String) = flow {
        emit(Resource.loading(null))

        try {
            val callResults = api.searchMovies(query)
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.movies.asModels()))
            } else {
                emit(Resource.error(null, callResults.message()))
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
        }
    }.flowOn(Dispatchers.IO)

    fun searchTVShows(query: String) = flow {
        emit(Resource.loading(null))

        try {
            val callResults = api.searchTVShows(query)
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.tvShows.asModels()))
            } else {
                emit(Resource.error(null, callResults.message()))
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
        }
    }.flowOn(Dispatchers.IO)
}