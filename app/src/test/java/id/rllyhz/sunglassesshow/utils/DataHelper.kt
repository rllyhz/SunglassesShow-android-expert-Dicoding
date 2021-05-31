package id.rllyhz.sunglassesshow.utils

import androidx.lifecycle.MutableLiveData
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.domain.model.Movie
import id.rllyhz.core.domain.model.TVShow
import id.rllyhz.core.vo.Resource

object DataHelper {
    fun getAllMovies() =
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

    fun getAllTVShows() =
        listOf(
            TVShow(
                1, "", "", "testingTVShow1", "",
                0, 0f, "", "", "", ""
            ),
            TVShow(
                2, "", "", "testingMovie2", "",
                0, 0f, "", "", "", ""
            )
        )

    fun getMovie() =
        Movie(
            1, "", "", "testingMovie1", "",
            0, 0f, "", "", "", ""
        )

    fun getTVShow() =
        TVShow(
            1, "", "", "testingTVShow1", "",
            0, 0f, "", "", "", ""
        )

    fun getAllFavMovies() =
        listOf(
            FavMovie(
                1, "", "", "movie1", 0, 0f
            ),
            FavMovie(
                2, "", "", "movie2", 0, 0f
            )
        )

    fun getAllFavTVShows() =
        listOf(
            FavTVShow(
                1, "", "", "tvShow1", 0, 0f
            ),
            FavTVShow(
                2, "", "", "tvShow2", 0, 0f
            )
        )

    fun getFavMovie(): FavMovie =
        FavMovie(
            1, "", "", "movie1", 0, 0f
        )

    fun getFavTVShow(): FavTVShow =
        FavTVShow(
            1, "", "", "tvShow1", 0, 0f
        )

    fun getMoviesLiveData(): MutableLiveData<Resource<List<Movie>>> {
        val movies = MutableLiveData<Resource<List<Movie>>>()
        movies.value = Resource.success(getAllMovies())

        return movies
    }

    fun getTVShowsLiveData(): MutableLiveData<Resource<List<TVShow>>> {
        val tvShows = MutableLiveData<Resource<List<TVShow>>>()
        tvShows.value = Resource.success(getAllTVShows())

        return tvShows
    }

    fun getDetailMovieLiveData(): MutableLiveData<Resource<Movie>> {
        val movie = MutableLiveData<Resource<Movie>>()
        movie.value = Resource.success(getMovie())

        return movie
    }

    fun getDetailTVShowLiveData(): MutableLiveData<Resource<TVShow>> {
        val tvShow = MutableLiveData<Resource<TVShow>>()
        tvShow.value = Resource.success(getTVShow())

        return tvShow
    }
}