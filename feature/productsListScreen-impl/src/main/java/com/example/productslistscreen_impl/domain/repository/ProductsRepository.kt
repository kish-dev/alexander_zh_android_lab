package com.example.productslistscreen_impl.domain.repository

import com.example.network_api.models.NetworkApiResult
import com.example.productslistscreen_impl.domain.models.ApiResult
import com.example.productslistscreen_impl.domain.models.ProductInListDomainDTO

interface ProductsRepository {
    suspend fun checkProductsChanges(
        productsFromApi: List<ProductInListDomainDTO>,
        productsFromDb: List<ProductInListDomainDTO>
    ): List<ProductInListDomainDTO>
    suspend fun getProducts(): NetworkApiResult<List<ProductInListDomainDTO>>
    suspend fun getProductById(guid: String): ProductInListDomainDTO
    suspend fun toggleFavorite(guid: String, isFavorite: Boolean)
    suspend fun getProductsDB(): ApiResult<List<ProductInListDomainDTO>>
    suspend fun incrementViewCount(guid: String)
}