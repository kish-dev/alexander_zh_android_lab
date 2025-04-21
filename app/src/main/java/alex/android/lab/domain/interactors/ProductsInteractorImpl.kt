package alex.android.lab.domain.interactors

import alex.android.lab.domain.exception.DomainException
import alex.android.lab.domain.repositories.ProductsRepository

class ProductsInteractorImpl(
    private val productsRepository: ProductsRepository
): ProductsInteractor{
    override fun getProducts() = productsRepository.getProducts()
    override fun getProductById(guid: String) = productsRepository.getProductById(guid)?: throw DomainException("Products with id: $guid not found")
}