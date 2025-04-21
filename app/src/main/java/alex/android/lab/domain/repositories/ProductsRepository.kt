package alex.android.lab.domain.repositories

import alex.android.lab.presentation.viewObject.ProductInListVO

interface ProductsRepository {
    fun getProducts(): List<ProductInListVO>
    fun getProductById(guid: String): ProductInListVO?
}