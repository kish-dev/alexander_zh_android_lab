package alex.android.lab.domain.interactors

import alex.android.lab.presentation.viewObject.ProductInListVO

interface ProductsInteractor {
    fun getProducts(): List<ProductInListVO>
    fun getProductById(guid: String): ProductInListVO?
}