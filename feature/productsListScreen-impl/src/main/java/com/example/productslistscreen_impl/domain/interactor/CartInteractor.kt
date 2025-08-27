package com.example.productslistscreen_impl.domain.interactor

interface CartInteractor {
    suspend fun updateInCartCount(guid: String, count: Int)
    suspend fun getInCartCount(guid: String): Int
    suspend fun getInCartProductsCount(): Int
}