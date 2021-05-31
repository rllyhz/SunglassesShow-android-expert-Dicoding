package id.rllyhz.sunglassesshow.utils

import androidx.lifecycle.MutableLiveData
import id.rllyhz.core.domain.model.Movie
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.core.vo.ApiResponse

object DataHelper {

    fun getMovies(): MutableLiveData<ApiResponse<List<Movie>>> {
        val movies = MutableLiveData<ApiResponse<List<Movie>>>()

        movies.value = ApiResponse.Success(
            listOf(
                Movie(
                    1, "", "", "testingMovie1", "",
                    0, 0f, "", "", "", ""
                ),
                Movie(
                    2, "", "", "testingMovie2", "",
                    0, 0f, "", "", "", ""
                )
            )
        )

        return movies
    }

    fun getEmptyMovies(): MutableLiveData<ApiResponse<List<Movie>>> {
        val emptyMovies = MutableLiveData<ApiResponse<List<Movie>>>()
        emptyMovies.value = ApiResponse.Success(
            listOf()
        )

        return emptyMovies
    }

    fun getMoviesError(): MutableLiveData<ApiResponse<List<Movie>>> {
        val errorMovies = MutableLiveData<ApiResponse<List<Movie>>>()
        errorMovies.value = ApiResponse.Error("Error while Loading data: No Internet!")

        return errorMovies
    }

    fun getDetailMovie(): MutableLiveData<ApiResponse<Movie>> {
        val movie = MutableLiveData<ApiResponse<Movie>>()

        movie.value = ApiResponse.Success(
            Movie(
                1, "", "", "testingMovie1", "",
                0, 0f, "", "", "", ""
            )
        )

        return movie
    }

    fun getErrorDetailMovie(): MutableLiveData<ApiResponse<Movie>> {
        val errorMovie = MutableLiveData<ApiResponse<Movie>>()
        errorMovie.value = ApiResponse.Error("Error Load Data: No Internet")

        return errorMovie
    }

    fun getTVShows(): MutableLiveData<ApiResponse<List<TVShow>>> {
        val tvShows = MutableLiveData<ApiResponse<List<TVShow>>>()

        tvShows.value = ApiResponse.Success(
            listOf(
                TVShow(
                    1, "", "", "testingTVShow1", "",
                    0, 0f, "", "", "", ""
                ),
                TVShow(
                    2, "", "", "testingTVShow2", "",
                    0, 0f, "", "", "", ""
                )
            )
        )

        return tvShows
    }

    fun getEmptyTVShows(): MutableLiveData<ApiResponse<List<TVShow>>> {
        val emptyTVShows = MutableLiveData<ApiResponse<List<TVShow>>>()
        emptyTVShows.value = ApiResponse.Success(
            listOf()
        )

        return emptyTVShows
    }

    fun getTVShowsError(): MutableLiveData<ApiResponse<List<TVShow>>> {
        val errorTVShows = MutableLiveData<ApiResponse<List<TVShow>>>()
        errorTVShows.value = ApiResponse.Error("Error while Loading data: No Internet!")

        return errorTVShows
    }

    fun getDetailTVShow(): MutableLiveData<ApiResponse<TVShow>> {
        val tvShow = MutableLiveData<ApiResponse<TVShow>>()

        tvShow.value = ApiResponse.Success(
            TVShow(
                1, "", "", "testingMovie1", "",
                0, 0f, "", "", "", ""
            )
        )

        return tvShow
    }

    fun getErrorDetailTVShow(): MutableLiveData<ApiResponse<TVShow>> {
        val errorTVShow = MutableLiveData<ApiResponse<TVShow>>()
        errorTVShow.value = ApiResponse.Error("Error Load Data: No Internet")

        return errorTVShow
    }
}