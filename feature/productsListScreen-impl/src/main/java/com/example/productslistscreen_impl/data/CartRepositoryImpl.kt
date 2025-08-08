package com.example.productScreen_impl.data

import com.example.database_api.DatabaseApi
import com.example.productslistscreen_impl.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val dao: DatabaseApi
): CartRepository {
    override suspend fun updateInCartCount(guid: String, count: Int) {
        dao.updateInCartCount(guid, count)
    }

    override suspend fun getInCartCount(guid: String): Int {
        return dao.getInCartCount(guid)
    }

    override suspend fun getInCartProductsCount(): Int {
        return dao.getInCartProductsCount()
    }
}