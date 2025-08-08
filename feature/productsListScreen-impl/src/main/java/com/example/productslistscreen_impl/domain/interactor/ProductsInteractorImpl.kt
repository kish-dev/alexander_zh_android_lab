package com.example.productScreen_impl.domain


import com.example.productScreen_impl.data.UIStatesMapper
import com.example.productslistscreen_impl.domain.interactor.ProductsInteractor
import com.example.productslistscreen_impl.domain.models.ProductInListDomainDTO
import com.example.productslistscreen_impl.domain.models.UIStates
import com.example.productslistscreen_impl.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsInteractorImpl @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val uiStatesMapper: UIStatesMapper
): ProductsInteractor {

    override suspend fun getProducts(): UIStates<List<ProductInListDomainDTO>> {
        return productsRepository.getProducts().let { uiStatesMapper.NetworkApiResultToUIStates(it) }
    }
    override suspend fun getProductById(guid: String): ProductInListDomainDTO {
        return productsRepository.getProductById(guid)
    }
    override suspend fun toggleFavorite(productId: String, favorite: Boolean) {
        productsRepository.toggleFavorite(productId, favorite)
    }
    override suspend fun getProductsDB(): UIStates<List<ProductInListDomainDTO>> {
        return productsRepository.getProductsDB().let { uiStatesMapper.ApiResultToUIStates(it) }
    }
    override suspend fun incrementViewCount(guid: String) {
        productsRepository.incrementViewCount(guid)
    }
}