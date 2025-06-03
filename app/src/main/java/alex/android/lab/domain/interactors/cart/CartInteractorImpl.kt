package alex.android.lab.domain.interactors.cart

import alex.android.lab.domain.repositories.cart.CartRepository

class CartInteractorImpl(
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