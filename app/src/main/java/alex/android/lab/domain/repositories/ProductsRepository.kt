package alex.android.lab.domain.repositories

import alex.android.lab.domain.ApiResult.ApiResult
import alex.android.lab.presentation.UiStates.UIStates
import alex.android.lab.domain.dto.ProductInListDomainDTO

interface ProductsRepository {
    suspend fun checkProductsChanges(
        productsFromApi: List<ProductInListDomainDTO>,
        productsFromDb: List<ProductInListDomainDTO>
    ): List<ProductInListDomainDTO>
    suspend fun getProducts(): ApiResult<List<ProductInListDomainDTO>>
    suspend fun getProductById(guid: String): ProductInListDomainDTO
    suspend fun toggleFavorite(guid: String, isFavorite: Boolean)
    suspend fun getProductsDB(): ApiResult<List<ProductInListDomainDTO>>
    suspend fun incrementViewCount(guid: String)
}