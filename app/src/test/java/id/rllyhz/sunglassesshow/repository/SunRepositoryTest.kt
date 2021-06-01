package id.rllyhz.sunglassesshow.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import id.rllyhz.core.data.SunGlassesShowRepository
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.vo.Resource
import id.rllyhz.sunglassesshow.utils.CoroutineTestRule
import id.rllyhz.sunglassesshow.utils.DataHelper
import id.rllyhz.sunglassesshow.utils.PagedListUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SunRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var repo: SunGlassesShowRepository

    private val idForTesting = 2

    @Test
    fun getAllMovies() = coroutineTestRule.testDispatcher.runBlockingTest {
        val moviesDummyData = DataHelper.getAllMovies()
        val moviesDummyFlow = flow { emit(Resource.success(moviesDummyData)) }

        assertNotNull(moviesDummyData)
        assertEquals(moviesDummyData.size, moviesDummyFlow.first().data?.size)

        `when`(repo.getMovies()).thenReturn(moviesDummyFlow)
        val actualMovies = repo.getMovies().first()
        verify(repo).getMovies()

        assertNotNull(actualMovies)
        assertEquals(
            moviesDummyFlow.first().data?.size,
            actualMovies.data?.size
        )
    }

    @Test
    fun getAllTVShows() = coroutineTestRule.testDispatcher.runBlockingTest {
        val tvShowsDummyData = DataHelper.getAllTVShows()
        val tvShowsDummyFlow = flow { emit(Resource.success(tvShowsDummyData)) }

        assertNotNull(tvShowsDummyData)
        assertEquals(tvShowsDummyData.size, tvShowsDummyFlow.first().data?.size)

        `when`(repo.getTVShows()).thenReturn(tvShowsDummyFlow)
        val actualTVShows = repo.getTVShows().first()
        verify(repo).getTVShows()

        assertNotNull(actualTVShows)
        assertEquals(
            tvShowsDummyFlow.first().data?.size,
            actualTVShows.data?.size
        )
    }

    @Test
    fun getMovieByGivenId() = coroutineTestRule.testDispatcher.runBlockingTest {
        val movieDummyData = DataHelper.getMovie()
        val movieDummyFlow = flow { emit(Resource.success(movieDummyData)) }

        assertNotNull(movieDummyData)
        assertEquals(movieDummyData.title, movieDummyFlow.first().data?.title)

        `when`(repo.getMovieById(idForTesting)).thenReturn(movieDummyFlow)
        val actualMovie = repo.getMovieById(idForTesting).first()
        verify(repo).getMovieById(idForTesting)

        assertNotNull(actualMovie)
        assertEquals(
            movieDummyFlow.first().data?.title,
            actualMovie.data?.title
        )
    }

    @Test
    fun getTVShowByGivenId() = coroutineTestRule.testDispatcher.runBlockingTest {
        val tvShowDummyData = DataHelper.getTVShow()
        val tvShowDummyFlow = flow { emit(Resource.success(tvShowDummyData)) }

        assertNotNull(tvShowDummyData)
        assertEquals(tvShowDummyData.title, tvShowDummyFlow.first().data?.title)

        `when`(repo.getTVShowById(idForTesting)).thenReturn(tvShowDummyFlow)
        val actualTVShow = repo.getTVShowById(idForTesting).first()
        verify(repo).getTVShowById(idForTesting)

        assertNotNull(actualTVShow)
        assertEquals(
            tvShowDummyFlow.first().data?.title,
            actualTVShow.data?.title
        )
    }

    @Test
    fun getSimilarMoviesOfGivenId() = coroutineTestRule.testDispatcher.runBlockingTest {
        val similarMoviesDummyData = DataHelper.getAllMovies()
        val similarMoviesDummyFlow = flow { emit(Resource.success(similarMoviesDummyData)) }

        assertNotNull(similarMoviesDummyData)
        assertEquals(similarMoviesDummyData.size, similarMoviesDummyFlow.first().data?.size)

        `when`(repo.getSimilarMoviesOf(idForTesting)).thenReturn(similarMoviesDummyFlow)
        val actualSimilarMovies = repo.getSimilarMoviesOf(idForTesting).first()
        verify(repo).getSimilarMoviesOf(idForTesting)

        assertNotNull(actualSimilarMovies)
        assertEquals(
            similarMoviesDummyFlow.first().data?.size,
            actualSimilarMovies.data?.size
        )
    }

    @Test
    fun getSimilarTVShowsOfGivenId() = coroutineTestRule.testDispatcher.runBlockingTest {
        val similarTvShowsDummyData = DataHelper.getAllTVShows()
        val similarTvShowsDummyFlow = flow { emit(Resource.success(similarTvShowsDummyData)) }

        assertNotNull(similarTvShowsDummyData)
        assertEquals(similarTvShowsDummyData.size, similarTvShowsDummyFlow.first().data?.size)

        `when`(repo.getSimilarTVShowsOf(idForTesting)).thenReturn(similarTvShowsDummyFlow)
        val actualSimilarTVShows = repo.getSimilarTVShowsOf(idForTesting).first()
        verify(repo).getSimilarTVShowsOf(idForTesting)

        assertNotNull(actualSimilarTVShows)
        assertEquals(
            similarTvShowsDummyFlow.first().data?.size,
            actualSimilarTVShows.data?.size
        )
    }

    @Test
    fun getFavMovies() = coroutineTestRule.testDispatcher.runBlockingTest {
        val favMoviesDataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavMovie>
        `when`(repo.getFavMovies()).thenReturn(favMoviesDataSourceFactory)
        repo.getFavMovies()

        val favMovies = Resource.success(PagedListUtil.mockPagedList(DataHelper.getAllFavMovies()))
        verify(repo).getFavMovies()

        assertNotNull(favMovies)

        assertEquals(
            DataHelper.getAllFavMovies().size,
            favMovies.data?.size
        )
    }

    @Test
    fun getFavTVShows() = coroutineTestRule.testDispatcher.runBlockingTest {
        val favTVShowsDataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavTVShow>
        `when`(repo.getFavTVShows()).thenReturn(favTVShowsDataSourceFactory)
        repo.getFavTVShows()

        val favTVShows =
            Resource.success(PagedListUtil.mockPagedList(DataHelper.getAllFavTVShows()))
        verify(repo).getFavTVShows()

        assertNotNull(favTVShows)

        assertEquals(
            DataHelper.getAllFavTVShows().size,
            favTVShows.data?.size
        )
    }

    @Test
    fun getFavMovieById() = coroutineTestRule.testDispatcher.runBlockingTest {
        val favMovieDummyData = DataHelper.getFavMovie()
        val favMovieDummyFlow = flow { emit(favMovieDummyData) }

        assertNotNull(favMovieDummyData)
        assertEquals(favMovieDummyData.title, favMovieDummyFlow.first().title)

        `when`(repo.getFavMovieById(idForTesting)).thenReturn(favMovieDummyFlow)
        val actualFavMovie = repo.getFavMovieById(idForTesting).first()
        verify(repo).getFavMovieById(idForTesting)

        assertNotNull(actualFavMovie)
        assertEquals(
            favMovieDummyFlow.first().title,
            actualFavMovie?.title
        )
    }

    @Test
    fun getFavTVShowById() = coroutineTestRule.testDispatcher.runBlockingTest {
        val favTVShowDummyData = DataHelper.getFavTVShow()
        val favTVShowDummyFlow = flow { emit(favTVShowDummyData) }

        assertNotNull(favTVShowDummyData)
        assertEquals(favTVShowDummyData.title, favTVShowDummyFlow.first().title)

        `when`(repo.getFavTVShowById(idForTesting)).thenReturn(favTVShowDummyFlow)
        val actualFavTVShow = repo.getFavTVShowById(idForTesting).first()
        verify(repo).getFavTVShowById(idForTesting)

        assertNotNull(actualFavTVShow)
        assertEquals(
            favTVShowDummyFlow.first().title,
            actualFavTVShow?.title
        )
    }

    @Test
    fun addAndThenRemoveMovieFromFavorite() = coroutineTestRule.testDispatcher.runBlockingTest {
        val favMovieDataDummy = DataHelper.getFavMovie()
        // adding scenario
        `when`(repo.addFavMovie(favMovieDataDummy)).thenReturn(favMovieDataDummy.id.toLong())
        val addedFavMovie = repo.addFavMovie(favMovieDataDummy)
        assertNotNull(addedFavMovie)

        // removing scenario
        `when`(repo.deleteFavMovie(favMovieDataDummy)).thenReturn(favMovieDataDummy.id)
        val removedFavMovie = repo.deleteFavMovie(favMovieDataDummy)
        assertNotNull(removedFavMovie)
    }

    @Test
    fun addAndThenRemoveTVShowFromFavorite() = coroutineTestRule.testDispatcher.runBlockingTest {
        val favTVShowDataDummy = DataHelper.getFavTVShow()
        // adding scenario
        `when`(repo.addFavTVShow(favTVShowDataDummy))
            .thenReturn(favTVShowDataDummy.id.toLong())
        val addedFavTVShow = repo.addFavTVShow(favTVShowDataDummy)
        assertNotNull(addedFavTVShow)

        // removing scenario
        `when`(repo.deleteFavTVShow(favTVShowDataDummy))
            .thenReturn(favTVShowDataDummy.id)
        val removedFavTVShow = repo.deleteFavTVShow(favTVShowDataDummy)
        assertNotNull(removedFavTVShow)
    }
}