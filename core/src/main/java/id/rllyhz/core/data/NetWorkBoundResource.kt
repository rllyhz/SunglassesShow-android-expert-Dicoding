package id.rllyhz.core.data

import id.rllyhz.core.vo.ApiResponse
import id.rllyhz.core.vo.Resource
import kotlinx.coroutines.flow.*

abstract class NetWorkBoundResource<ResultType, RequestType> {

    private val results: Flow<Resource<ResultType>> = flow {
        emit(Resource.loading(null))
        val dbSource = loadFromDB().first()

        if (shouldFetch(dbSource)) {
            emit(Resource.loading(dbSource))

            when (val callResults = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(callResults.data)
                    emitAll(loadFromDB().map { Resource.success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.error<ResultType>(null, callResults.message))
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.success(it) })
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = results
}