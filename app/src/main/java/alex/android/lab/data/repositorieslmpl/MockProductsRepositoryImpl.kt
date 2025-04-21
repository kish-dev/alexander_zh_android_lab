package alex.android.lab.data.repositorieslmpl

import alex.android.lab.data.mappers.ProductListMapper
import alex.android.lab.data.mockData.productInListDTOs
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.presentation.viewObject.ProductInListVO


class MockProductsRepositoryImpl: ProductsRepository {

    override fun getProducts(): List<ProductInListVO> {
        return productInListDTOs.map { ProductListMapper.toVO(it) }
    }

    override fun getProductById(guid: String): ProductInListVO? {
        return productInListDTOs.firstOrNull { it.guid == guid }?.let { ProductListMapper.toVO(it) }
    }
}