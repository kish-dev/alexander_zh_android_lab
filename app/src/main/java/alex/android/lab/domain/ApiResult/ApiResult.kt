package alex.android.lab.domain.ApiResult

open class ApiResult<T> {
    data class Loading<T>(val data: T? = null) : ApiResult<T>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val error: String, val code: Int? = null) : ApiResult<T>()
}