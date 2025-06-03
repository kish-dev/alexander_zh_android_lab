package alex.android.lab.domain.interactors.cart

interface CartInteractor {
    suspend fun updateInCartCount(guid: String, count: Int)
    suspend fun getInCartCount(guid: String): Int
    suspend fun getInCartProductsCount(): Int
}