package id.rllyhz.core.data.remote

import id.rllyhz.core.api.ApiEndpoint
import id.rllyhz.core.utils.ext.asModels
import id.rllyhz.core.utils.testing.EspressoIdlingResource
import id.rllyhz.core.vo.ApiResponse
import id.rllyhz.core.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: ApiEndpoint
) {

    suspend fun getAllMovies() = flow {
        EspressoIdlingResource.increment()

        try {
            val callResults = api.discoverMovies()
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                if (data.movies.isNotEmpty()) {
                    emit(ApiResponse.Success(data.movies.asModels()))
                    EspressoIdlingResource.decrement()
                } else {
                    emit(ApiResponse.Empty)
                    EspressoIdlingResource.decrement()
                }
            } else {
                emit(ApiResponse.Error(callResults.message()))
                EspressoIdlingResource.decrement()
            }
        } catch (exc: Exception) {
            emit(ApiResponse.Error(exc.message ?: "Error occurred!"))
            EspressoIdlingResource.decrement()
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getAllTVShows() = flow {
        EspressoIdlingResource.increment()

        try {
            val callResults = api.discoverTVShows()
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                if (data.tvShows.isNotEmpty()) {
                    emit(ApiResponse.Success(data.tvShows.asModels()))
                    EspressoIdlingResource.decrement()
                } else {
                    emit(ApiResponse.Empty)
                    EspressoIdlingResource.decrement()
                }
            } else {
                emit(ApiResponse.Error(callResults.message()))
                EspressoIdlingResource.decrement()
            }
        } catch (exc: Exception) {
            emit(ApiResponse.Error(exc.message ?: "Error occurred!"))
            EspressoIdlingResource.decrement()
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieById(id: Int) = flow {
        EspressoIdlingResource.increment()

        try {
            val callResults = api.getMovieDetailOf(id)
            val data = callResults.body()

            if (callResults.isSuccessful && callResults.code() == 200) {
                if (data != null) {
                    emit(ApiResponse.Success(data.asModels()))
                    EspressoIdlingResource.decrement()
                } else {
                    emit(ApiResponse.Empty)
                    EspressoIdlingResource.decrement()
                }
            } else {
                emit(ApiResponse.Error(callResults.message()))
                EspressoIdlingResource.decrement()
            }
        } catch (exc: Exception) {
            emit(ApiResponse.Error(exc.message ?: "Error occurred!"))
            EspressoIdlingResource.decrement()
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getTVShowById(id: Int) = flow {
        EspressoIdlingResource.increment()

        try {
            val callResults = api.getTvShowDetailOf(id)
            val data = callResults.body()

            if (callResults.isSuccessful && callResults.code() == 200) {
                if (data != null) {
                    emit(ApiResponse.Success(data.asModels()))
                    EspressoIdlingResource.decrement()
                } else {
                    emit(ApiResponse.Empty)
                    EspressoIdlingResource.decrement()
                }
            } else {
                emit(ApiResponse.Error(callResults.message()))
                EspressoIdlingResource.decrement()
            }
        } catch (exc: Exception) {
            emit(ApiResponse.Error(exc.message ?: "Error occurred!"))
            EspressoIdlingResource.decrement()
        }
    }.flowOn(Dispatchers.IO)

    fun getSimilarMoviesOfMovie(id: Int) = flow {
        EspressoIdlingResource.increment()
        emit(Resource.loading(null))

        try {
            val callResults = api.getSimilarMoviesOf(id)
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.movies.asModels()))
                EspressoIdlingResource.decrement()
            } else {
                emit(Resource.error(null, callResults.message()))
                EspressoIdlingResource.decrement()
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
            EspressoIdlingResource.decrement()
        }
    }.flowOn(Dispatchers.IO)

    fun getSimilarTVShowsOfMovie(id: Int) = flow {
        EspressoIdlingResource.increment()
        emit(Resource.loading(null))

        try {
            val callResults = api.getSimilarTVShowsOf(id)
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.tvShows.asModels()))
                EspressoIdlingResource.decrement()
            } else {
                emit(Resource.error(null, callResults.message()))
                EspressoIdlingResource.decrement()
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
            EspressoIdlingResource.decrement()
        }
    }.flowOn(Dispatchers.IO)

    fun searchMovies(query: String) = flow {
        EspressoIdlingResource.increment()
        emit(Resource.loading(null))

        try {
            val callResults = api.searchMovies(query)
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.movies.asModels()))
                EspressoIdlingResource.decrement()
            } else {
                emit(Resource.error(null, callResults.message()))
                EspressoIdlingResource.decrement()
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
            EspressoIdlingResource.decrement()
        }
    }.flowOn(Dispatchers.IO)

    fun searchTVShows(query: String) = flow {
        EspressoIdlingResource.increment()
        emit(Resource.loading(null))

        try {
            val callResults = api.searchTVShows(query)
            val data = callResults.body()

            if (callResults.isSuccessful && data != null) {
                emit(Resource.success(data.tvShows.asModels()))
                EspressoIdlingResource.decrement()
            } else {
                emit(Resource.error(null, callResults.message()))
                EspressoIdlingResource.decrement()
            }
        } catch (exc: Exception) {
            emit(Resource.error(null, exc.message ?: "Error occurred!"))
            EspressoIdlingResource.decrement()
        }
    }.flowOn(Dispatchers.IO)
}