package alex.android.lab.domain.interactors

import alex.android.lab.domain.UiStates.ApiResult
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.presentation.viewObject.ProductInListVO

class ProductsInteractorImpl(
    private val productsRepository: ProductsRepository
): ProductsInteractor{

    override suspend fun getProducts(): ApiResult<List<ProductInListVO>> {
        return productsRepository.getProducts()
    }

    override suspend fun getProductById(guid: String): ProductInListVO {
        return productsRepository.getProductById(guid)
    }

    override suspend fun toggleFavorite(productId: String, favorite: Boolean) {
        productsRepository.toggleFavorite(productId, favorite)
    }

    override suspend fun getProductsDB(): ApiResult<List<ProductInListVO>> {
        return productsRepository.getProductsDB()
    }
    override suspend fun incrementViewCount(guid: String) {
        productsRepository.incrementViewCount(guid)
    }
}