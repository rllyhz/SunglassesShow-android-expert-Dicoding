package id.rllyhz.core.vo

sealed class ApiResponse<out T> {
    data class Success<out C>(val data: C) : ApiResponse<C>()
    data class Error(val message: String) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}