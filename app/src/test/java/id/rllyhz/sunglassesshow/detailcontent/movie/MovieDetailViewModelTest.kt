package id.rllyhz.sunglassesshow.detailcontent.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.rllyhz.core.domain.usecase.SunGlassesShowUseCase
import id.rllyhz.sunglassesshow.utils.CoroutineTestRule
import id.rllyhz.sunglassesshow.utils.DataHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: MovieDetailViewModel

    @Mock
    private lateinit var useCase: SunGlassesShowUseCase

    private val movieIdForTesting = 1

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(useCase, movieIdForTesting)
    }

    @Test
    fun `get detail Movie`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val detailMovieDataDummy = DataHelper.getDetailMovieLiveData()
        `when`(useCase.getMovieById(movieIdForTesting)).thenReturn(detailMovieDataDummy)

        // check viewModel not null
        assertNotNull(viewModel)
        viewModel.initAllRequiredData()
        val actualDetailMovie = viewModel.getDetailMovie()

        assertNotNull(actualDetailMovie)

        assertEquals(
            detailMovieDataDummy.value,
            actualDetailMovie.value
        )
    }

    @Test
    fun `get similar of Movie`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val similarMoviesDataDummy = DataHelper.getMoviesLiveData()
        `when`(useCase.getSimilarMoviesOf(movieIdForTesting)).thenReturn(similarMoviesDataDummy)

        // check viewModel not null
        assertNotNull(viewModel)
        viewModel.initAllRequiredData()
        val actualSimilarMovies = viewModel.getSimilarMovies()

        assertNotNull(actualSimilarMovies)

        assertEquals(
            similarMoviesDataDummy.value,
            actualSimilarMovies.value
        )
    }

    @Test
    fun `add and then remove movie from favorite`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            val favMovieDataDummy = DataHelper.getFavMovie()
            // adding scenario
            `when`(useCase.addFavMovie(favMovieDataDummy)).thenReturn(favMovieDataDummy.id.toLong())
            val addedFavMovie = useCase.addFavMovie(favMovieDataDummy)
            assertNotNull(addedFavMovie)

            // removing scenario
            `when`(useCase.deleteFavMovie(favMovieDataDummy)).thenReturn(favMovieDataDummy.id)
            val removedFavMovie = useCase.deleteFavMovie(favMovieDataDummy)
            assertNotNull(removedFavMovie)
        }
}