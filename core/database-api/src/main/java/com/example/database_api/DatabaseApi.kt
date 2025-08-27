package com.example.database_api

import com.example.database_api.models.ProductInListDbDTO
import com.example.database_api.models.ProductInListEntity

interface DatabaseApi {
    suspend fun getProducts(): List<ProductInListEntity>
    suspend fun getProductByGuid(guid: String): ProductInListEntity
    suspend fun updateFavoriteStatus(guid: String, isFavorite: Boolean)
    suspend fun incrementViewCount(guid: String)
    suspend fun insertAllProducts(products: List<ProductInListDbDTO>)
    suspend fun insertProduct(product: ProductInListDbDTO)
    suspend fun updateInCartCount(guid: String, inCartCount: Int)
    suspend fun getInCartCount(guid: String): Int
    suspend fun getInCartProductsCount(): Int
}