package alex.android.lab.data.repositorieslmpl

import alex.android.lab.data.DataSource.LocalDataSource.Dao
import alex.android.lab.domain.repositories.cart.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val dao: Dao
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