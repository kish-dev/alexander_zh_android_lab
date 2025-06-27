package alex.android.lab.domain.UiStates.UIStatesMapper

import alex.android.lab.domain.ApiResult.ApiResult
import alex.android.lab.domain.UiStates.UIStates
import alex.android.lab.domain.dto.ProductInListDomainDTO
import javax.inject.Inject

class UIStatesMapper @Inject constructor(){
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