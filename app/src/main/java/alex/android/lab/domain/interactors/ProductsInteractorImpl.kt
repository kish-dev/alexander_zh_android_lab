package alex.android.lab.domain.interactors

import alex.android.lab.domain.UiStates.UIStates
import alex.android.lab.domain.UiStates.UIStatesMapper.UIStatesMapper
import alex.android.lab.domain.dto.ProductInListDomainDTO
import alex.android.lab.domain.repositories.ProductsRepository
import javax.inject.Inject

class ProductsInteractorImpl @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val uiStatesMapper: UIStatesMapper
): ProductsInteractor{

    override suspend fun getProducts(): UIStates<List<ProductInListDomainDTO>> {
        return productsRepository.getProducts().let { uiStatesMapper.toUIStates(it) }
    }
    override suspend fun getProductById(guid: String): ProductInListDomainDTO {
        return productsRepository.getProductById(guid)
    }
    override suspend fun toggleFavorite(productId: String, favorite: Boolean) {
        productsRepository.toggleFavorite(productId, favorite)
    }
    override suspend fun getProductsDB(): UIStates<List<ProductInListDomainDTO>> {
        return productsRepository.getProductsDB().let { uiStatesMapper.toUIStates(it) }
    }
    override suspend fun incrementViewCount(guid: String) {
        productsRepository.incrementViewCount(guid)
    }
}