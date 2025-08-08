package com.example.productScreen_impl.domain

import com.example.productslistscreen_impl.domain.interactor.CartInteractor
import com.example.productslistscreen_impl.domain.repository.CartRepository
import javax.inject.Inject

class CartInteractorImpl @Inject constructor(
    private val cartRepository: CartRepository
): CartInteractor {

    override suspend fun updateInCartCount(guid: String, count: Int) {
        cartRepository.updateInCartCount(guid, count)
    }

    override suspend fun getInCartCount(guid: String): Int {
        return cartRepository.getInCartCount(guid)
    }

    override suspend fun getInCartProductsCount(): Int {
        return cartRepository.getInCartProductsCount()
    }
}
