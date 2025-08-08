package com.example.productslistscreen_impl.domain.repository

interface CartRepository {
    suspend fun updateInCartCount(guid: String, count: Int)
    suspend fun getInCartCount(guid: String): Int
    suspend fun getInCartProductsCount(): Int
}