package com.example.productScreen_impl.data

import com.example.network_api.models.NetworkApiResult
import com.example.productslistscreen_impl.domain.models.ApiResult
import com.example.productslistscreen_impl.domain.models.ProductInListDomainDTO
import com.example.productslistscreen_impl.domain.models.UIStates
import javax.inject.Inject

class UIStatesMapper @Inject constructor(){
    fun NetworkApiResultToUIStates(result: NetworkApiResult<List<ProductInListDomainDTO>>): UIStates<List<ProductInListDomainDTO>> {
        return when (result) {
            is NetworkApiResult.Success -> UIStates.Success(result.data)
            is NetworkApiResult.Error -> UIStates.Error(result.error)
            is NetworkApiResult.Loading -> UIStates.Loading()
            else -> {
                UIStates.Error("Unknown error")
            }
        }
    }

    fun ApiResultToUIStates(result: ApiResult<List<ProductInListDomainDTO>>): UIStates<List<ProductInListDomainDTO>> {
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