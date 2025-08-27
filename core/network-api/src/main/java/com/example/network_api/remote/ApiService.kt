package com.example.network_api.remote

import com.example.network_api.models.ProductInListNetworkDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductInListNetworkDto>>
}