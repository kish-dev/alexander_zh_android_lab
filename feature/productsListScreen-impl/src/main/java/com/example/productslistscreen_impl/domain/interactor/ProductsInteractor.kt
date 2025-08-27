package com.example.productslistscreen_impl.domain.interactor

import com.example.productslistscreen_impl.domain.models.UIStates
import com.example.productslistscreen_impl.domain.models.ProductInListDomainDTO

interface ProductsInteractor {
    suspend fun getProducts(): UIStates<List<ProductInListDomainDTO>>
    suspend fun getProductById(guid: String): ProductInListDomainDTO
    suspend fun toggleFavorite(productId: String, favorite: Boolean)
    suspend fun getProductsDB(): UIStates<List<ProductInListDomainDTO>>
    suspend fun incrementViewCount(guid: String)
}