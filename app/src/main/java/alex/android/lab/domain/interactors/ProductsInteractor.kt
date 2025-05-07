package alex.android.lab.domain.interactors

import alex.android.lab.domain.UiStates.ApiResult
import alex.android.lab.presentation.viewObject.ProductInListVO

interface ProductsInteractor {
    suspend fun getProducts(): ApiResult<List<ProductInListVO>>
    suspend fun getProductById(guid: String): ProductInListVO?
    suspend fun toggleFavorite(productId: String, favorite: Boolean)
    suspend fun getProductsDB(): ApiResult<List<ProductInListVO>>
    suspend fun incrementViewCount(guid: String)
}