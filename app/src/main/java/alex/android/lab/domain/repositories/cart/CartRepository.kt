package alex.android.lab.domain.repositories.cart

interface CartRepository {
    suspend fun updateInCartCount(guid: String, count: Int)
    suspend fun getInCartCount(guid: String): Int
    suspend fun getInCartProductsCount(): Int
}