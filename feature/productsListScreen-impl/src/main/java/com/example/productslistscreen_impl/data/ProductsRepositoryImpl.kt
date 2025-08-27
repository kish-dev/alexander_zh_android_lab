package com.example.productScreen_impl.data

import com.example.database_api.DatabaseApi
import com.example.network_api.models.NetworkApiResult
import com.example.network_api.remote.RemoteDataSourceApi
import com.example.productslistscreen_impl.domain.models.ApiResult
import com.example.productslistscreen_impl.domain.models.ProductInListDomainDTO
import com.example.productslistscreen_impl.domain.repository.ProductsRepository
import javax.inject.Inject


class ProductsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSourceApi,
    private val productListMapper: ProductListMapperDTO,
    private val dao: DatabaseApi
): ProductsRepository {

    override suspend fun getProductsDB(): ApiResult<List<ProductInListDomainDTO>> {
        return ApiResult.Success(dao.getProducts().map { productListMapper.DbToDomainDTO(it) })
    }

    override suspend fun incrementViewCount(guid: String) {
        dao.incrementViewCount(guid)
    }

    override suspend fun checkProductsChanges(
        productsFromApi: List<ProductInListDomainDTO>,
        productsFromDb: List<ProductInListDomainDTO>
    ): List<ProductInListDomainDTO> {

        val productsFromDb = productsFromDb.toMutableList()

        productsFromDb.removeAll { oldProduct ->
            oldProduct.guid !in productsFromApi.map { it.guid }
        }

        productsFromApi.forEach { newProduct ->
            val existingProductIndex = productsFromDb.indexOfFirst { it.guid == newProduct.guid }

            if (existingProductIndex != -1) {
                productsFromDb[existingProductIndex] = newProduct.copy(
                    isFavorite = productsFromDb[existingProductIndex].isFavorite,
                    isInCart = productsFromDb[existingProductIndex].isInCart,
                    viewCount = productsFromDb[existingProductIndex].viewCount,
                    inCartCount = productsFromDb[existingProductIndex].inCartCount
                )
            } else {
                productsFromDb.add(newProduct)
            }
        }

        return productsFromDb

    }

    override suspend fun getProducts(): NetworkApiResult<List<ProductInListDomainDTO>> {
        return when (val result = remoteDataSource.getProducts()) {
            is NetworkApiResult.Success -> {

                val productsFromApi = result.data.map { productListMapper.NetworkToDomainDTO(it) }
                var productsFromDB = dao.getProducts().map { productListMapper.DbToDomainDTO(it) }

                if (productsFromDB.isEmpty()) {

                    val productsEntity = productsFromApi.map { productListMapper.DomainDtoToDB(it) }
                    dao.insertAllProducts(productsEntity)

                    productsFromDB = dao.getProducts().map { productListMapper.DbToDomainDTO(it) }
                    NetworkApiResult.Success(productsFromDB)

                } else {

                    val updatedProducts = checkProductsChanges(productsFromApi, productsFromDB)
                    val productsEntity = updatedProducts.map { productListMapper.DomainDtoToDB(it) }
                    dao.insertAllProducts(productsEntity)
                    productsFromDB = dao.getProducts().map { productListMapper.DbToDomainDTO(it) }

                    NetworkApiResult.Success(productsFromDB)
                }

            }
            else -> {
                NetworkApiResult.Error(result.toString())
            }
        }
    }

    override suspend fun getProductById(guid: String): ProductInListDomainDTO {
        val product = dao.getProductByGuid(guid)
        return product.let { productListMapper.DbToDomainDTO(it) }
    }

    override suspend fun toggleFavorite(guid: String, isFavorite: Boolean) {
        dao.updateFavoriteStatus(guid, isFavorite)
    }
}

