package id.rllyhz.sunglassesshow.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.rllyhz.core.domain.usecase.SunGlassesShowUseCase
import id.rllyhz.sunglassesshow.utils.CoroutineTestRule
import id.rllyhz.sunglassesshow.utils.DataHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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

@FlowPreview
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var useCase: SunGlassesShowUseCase

    @Before
    fun setUp() {
        viewModel = MainViewModel(useCase)
    }

    @Test
    fun `get all movies`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val moviesDummyData = DataHelper.getMoviesLiveData()
        `when`(useCase.getMovies()).thenReturn(moviesDummyData)

        // check viewModel not null
        assertNotNull(viewModel)
        viewModel.initData()
        val actualMovies = viewModel.movies

        assertNotNull(actualMovies)

        assertEquals(
            moviesDummyData.value,
            actualMovies.value
        )
    }

    @Test
    fun `get all tv shows`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val tvShowsDummyData = DataHelper.getTVShowsLiveData()
        `when`(useCase.getTVShows()).thenReturn(tvShowsDummyData)

        // check viewModel not null
        assertNotNull(viewModel)
        viewModel.initData()
        val actualTvShows = viewModel.tvShows

        assertNotNull(actualTvShows)

        assertEquals(
            tvShowsDummyData.value,
            actualTvShows.value
        )
    }
}