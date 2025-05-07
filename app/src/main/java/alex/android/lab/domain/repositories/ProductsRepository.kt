package alex.android.lab.domain.repositories

import alex.android.lab.domain.UiStates.ApiResult
import alex.android.lab.presentation.viewObject.ProductInListVO

interface ProductsRepository {
    suspend fun checkProductsChanges(
        productsFromApi: List<ProductInListVO>,
        productsFromDb: List<ProductInListVO>
    ): List<ProductInListVO>
    suspend fun getProducts(): ApiResult<List<ProductInListVO>>
    suspend fun getProductById(guid: String): ProductInListVO
    suspend fun toggleFavorite(guid: String, isFavorite: Boolean)
    suspend fun getProductsDB(): ApiResult<List<ProductInListVO>>
    suspend fun incrementViewCount(guid: String)
}