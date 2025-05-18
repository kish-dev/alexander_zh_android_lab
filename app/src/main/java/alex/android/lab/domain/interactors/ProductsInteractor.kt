package alex.android.lab.domain.interactors

import alex.android.lab.presentation.UiStates.UIStates
import alex.android.lab.domain.dto.ProductInListDomainDTO

interface ProductsInteractor {
    suspend fun getProducts(): UIStates<List<ProductInListDomainDTO>>
    suspend fun getProductById(guid: String): ProductInListDomainDTO
    suspend fun toggleFavorite(productId: String, favorite: Boolean)
    suspend fun getProductsDB(): UIStates<List<ProductInListDomainDTO>>
    suspend fun incrementViewCount(guid: String)
}