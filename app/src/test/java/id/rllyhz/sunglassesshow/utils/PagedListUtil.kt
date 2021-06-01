package id.rllyhz.sunglassesshow.utils

import androidx.paging.PagedList
import org.mockito.Mockito
import org.mockito.Mockito.`when`

object PagedListUtil {

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> mockPagedList(list: List<T>): PagedList<T> {
        val pagedList = Mockito.mock(PagedList::class.java) as PagedList<T>
        `when`(pagedList.size).thenReturn(list.size)
        return pagedList
    }
}