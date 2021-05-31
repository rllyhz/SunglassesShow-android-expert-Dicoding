package id.rllyhz.sunglassesshow.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import id.rllyhz.core.data.SunGlassesShowRepository
import id.rllyhz.core.data.local.LocalDataSource
import id.rllyhz.core.data.local.entity.FavMovie
import id.rllyhz.core.data.local.entity.FavTVShow
import id.rllyhz.core.data.remote.RemoteDataSource
import id.rllyhz.core.vo.Resource
import id.rllyhz.sunglassesshow.utils.CoroutineTestRule
import id.rllyhz.sunglassesshow.utils.DataHelper
import id.rllyhz.sunglassesshow.utils.PagedListUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)

    private lateinit var repo: SunGlassesShowRepository

    @Before
    fun setup() {
        repo = SunGlassesShowRepository(remote, local)
    }

    @Test
    fun getAllMovies() = coroutineTestRule.testDispatcher.runBlockingTest {
        //
    }

    @Test
    fun getAllTVShows() = coroutineTestRule.testDispatcher.runBlockingTest {
        //
    }

    @Test
    fun getMovieByGivenId() = coroutineTestRule.testDispatcher.runBlockingTest {
        //
    }

    @Test
    fun getTVShowByGivenId() = coroutineTestRule.testDispatcher.runBlockingTest {
        //
    }

    @Test
    fun getSimilarMoviesOfGivenId() = coroutineTestRule.testDispatcher.runBlockingTest {
        //
    }

    @Test
    fun getSimilarTVShowsOfGivenId() = coroutineTestRule.testDispatcher.runBlockingTest {
        //
    }

    @Test
    fun getFavMovies() = coroutineTestRule.testDispatcher.runBlockingTest {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavMovie>
        `when`(local.getFavMovies()).thenReturn(dataSourceFactory)
        repo.getFavMovies()

        val favMovies = Resource.success(PagedListUtil.mockPagedList(DataHelper.getAllFavMovies()))
        verify(local).getFavMovies()

        assertNotNull(favMovies)

        assertEquals(
            DataHelper.getAllFavMovies().size,
            favMovies.data?.size
        )
    }

    @Test
    fun getFavTVShows() = coroutineTestRule.testDispatcher.runBlockingTest {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavTVShow>
        `when`(local.getFavTVShows()).thenReturn(dataSourceFactory)
        repo.getFavTVShows()

        val favTVShows =
            Resource.success(PagedListUtil.mockPagedList(DataHelper.getAllFavTVShows()))
        verify(local).getFavTVShows()

        assertNotNull(favTVShows)

        assertEquals(
            DataHelper.getAllFavTVShows().size,
            favTVShows.data?.size
        )
    }

    @Test
    fun getFavMovieById() = coroutineTestRule.testDispatcher.runBlockingTest {
        //
    }

    @Test
    fun getFavTVShowById() = coroutineTestRule.testDispatcher.runBlockingTest {
        //
    }

    @Test
    fun addAndThenRemoveMovieFromFavorite() = coroutineTestRule.testDispatcher.runBlockingTest {
        val favMovieDataDummy = DataHelper.getFavMovie()
        // adding scenario
        `when`(local.addFavMovie(favMovieDataDummy)).thenReturn(favMovieDataDummy.id.toLong())
        val addedFavMovie = local.addFavMovie(favMovieDataDummy)
        assertNotNull(addedFavMovie)

        // removing scenario
        `when`(local.deleteFavMovie(favMovieDataDummy)).thenReturn(favMovieDataDummy.id)
        val removedFavMovie = local.deleteFavMovie(favMovieDataDummy)
        assertNotNull(removedFavMovie)
    }

    @Test
    fun addAndThenRemoveTVShowFromFavorite() = coroutineTestRule.testDispatcher.runBlockingTest {
        val favTVShowDataDummy = DataHelper.getFavTVShow()
        // adding scenario
        `when`(local.addFavTVShow(favTVShowDataDummy))
            .thenReturn(favTVShowDataDummy.id.toLong())
        val addedFavTVShow = local.addFavTVShow(favTVShowDataDummy)
        assertNotNull(addedFavTVShow)

        // removing scenario
        `when`(local.deleteFavTVShow(favTVShowDataDummy))
            .thenReturn(favTVShowDataDummy.id)
        val removedFavTVShow = local.deleteFavTVShow(favTVShowDataDummy)
        assertNotNull(removedFavTVShow)
    }
}