package alex.android.lab.domain.UiStates

open class UIStates<T> {
    data class Loading<T>(val data: T? = null) : UIStates<T>()
    data class Success<T>(val data: T) : UIStates<T>()
    data class Error<T>(val error: String, val code: Int? = null) : UIStates<T>()
}

