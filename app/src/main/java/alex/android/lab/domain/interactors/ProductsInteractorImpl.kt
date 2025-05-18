package alex.android.lab.domain.interactors

import alex.android.lab.presentation.UiStates.UIStates
import alex.android.lab.presentation.UiStates.UIStatesMapper
import alex.android.lab.domain.dto.ProductInListDomainDTO
import alex.android.lab.domain.repositories.ProductsRepository

class ProductsInteractorImpl(
    private val productsRepository: ProductsRepository
): ProductsInteractor{

    override suspend fun getProducts(): UIStates<List<ProductInListDomainDTO>> {
        return productsRepository.getProducts().let { UIStatesMapper.toUIStates(it) }
    }

    override suspend fun getProductById(guid: String): ProductInListDomainDTO {
        return productsRepository.getProductById(guid)
    }

    override suspend fun toggleFavorite(productId: String, favorite: Boolean) {
        productsRepository.toggleFavorite(productId, favorite)
    }

    override suspend fun getProductsDB(): UIStates<List<ProductInListDomainDTO>> {
        return productsRepository.getProductsDB().let { UIStatesMapper.toUIStates(it) }
    }
    override suspend fun incrementViewCount(guid: String) {
        productsRepository.incrementViewCount(guid)
    }
}