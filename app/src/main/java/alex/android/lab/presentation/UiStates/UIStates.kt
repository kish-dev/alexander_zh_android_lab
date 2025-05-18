package alex.android.lab.presentation.UiStates

import alex.android.lab.domain.ApiResult.ApiResult
import alex.android.lab.domain.dto.ProductInListDomainDTO
import alex.android.lab.presentation.viewObject.ProductInListVO

open class UIStates<T> {
    data class Loading<T>(val data: T? = null) : UIStates<T>()
    data class Success<T>(val data: T) : UIStates<T>()
    data class Error<T>(val error: String, val code: Int? = null) : UIStates<T>()
}

object UIStatesMapper {
    fun toUIStates(result: ApiResult<List<ProductInListDomainDTO>>): UIStates<List<ProductInListDomainDTO>> {
        return when (result) {
            is ApiResult.Success -> UIStates.Success(result.data)

            is ApiResult.Error -> UIStates.Error(result.error)

            is ApiResult.Loading -> UIStates.Loading()
            else -> {
                UIStates.Error("Unknown error")
            }
        }
    }
}
