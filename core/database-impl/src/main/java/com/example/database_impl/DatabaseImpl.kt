package com.example.database_impl

import com.example.database_api.DatabaseApi
import com.example.database_api.mappers.Mapper
import com.example.database_api.models.ProductInListDbDTO
import com.example.database_api.models.ProductInListEntity
import javax.inject.Inject

class DatabaseImpl@Inject constructor(
    private val dao: Dao,
    private val mapper: Mapper
) : DatabaseApi {
    override suspend fun getProducts(): List<ProductInListEntity> {
        return dao.getProducts()
    }

    override suspend fun getProductByGuid(guid: String): ProductInListEntity {
        return dao.getProductByGuid(guid)
    }

    override suspend fun updateFavoriteStatus(guid: String, isFavorite: Boolean) {
        dao.updateFavoriteStatus(guid, isFavorite)
    }

    override suspend fun incrementViewCount(guid: String) {
        dao.incrementViewCount(guid)
    }

    override suspend fun insertAllProducts(products: List<ProductInListDbDTO>) {
        val entities = products.map { mapper.toEntity(it) }
        dao.insertAllProducts(entities)
    }

    override suspend fun insertProduct(product: ProductInListDbDTO) {
        dao.insertProduct(mapper.toEntity(product))
    }

    override suspend fun updateInCartCount(guid: String, inCartCount: Int) {
        dao.updateInCartCount(guid, inCartCount)
    }

    override suspend fun getInCartCount(guid: String): Int {
        return dao.getInCartCount(guid)
    }

    override suspend fun getInCartProductsCount(): Int {
        return dao.getInCartProductsCount()
    }
}