package id.rllyhz.sunglassesshow.detailcontent.tvshow

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
class TVShowDetailViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: TVShowDetailViewModel

    @Mock
    private lateinit var useCase: SunGlassesShowUseCase

    private val tvShowIdForTesting = 1

    @Before
    fun setUp() {
        viewModel = TVShowDetailViewModel(useCase, tvShowIdForTesting)
    }

    @Test
    fun `get detail TVShow`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val detailTVShowDataDummy = DataHelper.getDetailTVShowLiveData()
        `when`(useCase.getTVShowById(tvShowIdForTesting)).thenReturn(detailTVShowDataDummy)

        // check viewModel not null
        assertNotNull(viewModel)
        viewModel.initAllRequiredData()
        val actualDetailTVShow = viewModel.getDetailTVShow()

        assertNotNull(actualDetailTVShow)
        assertEquals(
            detailTVShowDataDummy.value,
            actualDetailTVShow.value
        )
    }

    @Test
    fun `get similar of TVShow`() = coroutineTestRule.testDispatcher.runBlockingTest {
        val similarTVShowsDataDummy = DataHelper.getTVShowsLiveData()
        `when`(useCase.getSimilarTVShowsOf(tvShowIdForTesting))
            .thenReturn(similarTVShowsDataDummy)

        // check viewModel not null
        assertNotNull(viewModel)
        viewModel.initAllRequiredData()
        val actualSimilarTVShows = viewModel.getSimilarTVShows()

        assertNotNull(actualSimilarTVShows)

        assertEquals(
            similarTVShowsDataDummy.value,
            actualSimilarTVShows.value
        )
    }

    @Test
    fun `add and then remove tv show from favorite`() =
        coroutineTestRule.testDispatcher.runBlockingTest {
            val favTVShowDataDummy = DataHelper.getFavTVShow()
            // adding scenario
            `when`(useCase.addFavTVShow(favTVShowDataDummy))
                .thenReturn(favTVShowDataDummy.id.toLong())
            val addedFavTVShow = useCase.addFavTVShow(favTVShowDataDummy)
            assertNotNull(addedFavTVShow)

            // removing scenario
            `when`(useCase.deleteFavTVShow(favTVShowDataDummy))
                .thenReturn(favTVShowDataDummy.id)
            val removedFavTVShow = useCase.deleteFavTVShow(favTVShowDataDummy)
            assertNotNull(removedFavTVShow)
        }
}