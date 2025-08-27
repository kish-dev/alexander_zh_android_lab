package com.example.network_api.remote

import com.example.network_api.models.NetworkApiResult
import com.example.network_api.models.ProductInListNetworkDto

interface RemoteDataSourceApi {
    suspend fun getProducts(): NetworkApiResult<List<ProductInListNetworkDto>>
}