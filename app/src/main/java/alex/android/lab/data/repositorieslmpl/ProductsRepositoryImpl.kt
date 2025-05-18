package alex.android.lab.data.repositorieslmpl

import alex.android.lab.domain.ApiResult.ApiResult
import alex.android.lab.data.DataSource.LocalDataSource.Dao
import alex.android.lab.data.DataSource.LocalDataSource.mappers.EntityMapper
import alex.android.lab.data.DataSource.RemoteDataSource.RemoteDataSource
import alex.android.lab.data.mappers.ProductListMapper
import alex.android.lab.presentation.UiStates.UIStates
import alex.android.lab.domain.dto.ProductInListDomainDTO
import alex.android.lab.domain.repositories.ProductsRepository
import org.koin.core.component.KoinComponent


class ProductsRepositoryImpl (
    private val remoteDataSource: RemoteDataSource,
    private val dao: Dao
): ProductsRepository, KoinComponent {

    override suspend fun getProductsDB(): ApiResult<List<ProductInListDomainDTO>> {
        return ApiResult.Success(dao.getProducts().map { EntityMapper.toDomainDTO(it) })
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
                    viewCount = productsFromDb[existingProductIndex].viewCount
                )
            } else {
                productsFromDb.add(newProduct)
            }
        }

        return productsFromDb

    }

    override suspend fun getProducts(): ApiResult<List<ProductInListDomainDTO>> {
        return when (val result = remoteDataSource.getProducts()) {
            is ApiResult.Success -> {

                val productsFromApi = result.data.map { ProductListMapper.toDomainDTO(it) }
                var productsFromDB = dao.getProducts().map { EntityMapper.toDomainDTO(it) }

                if (productsFromDB.isEmpty()) {

                    val productsEntity = productsFromApi.map { EntityMapper.toDbEntity(it) }
                    dao.insertAllProducts(productsEntity)

                    productsFromDB = dao.getProducts().map { EntityMapper.toDomainDTO(it) }
                    ApiResult.Success(productsFromDB)

                } else {

                    val updatedProducts = checkProductsChanges(productsFromApi, productsFromDB).map {
                        EntityMapper.toDbEntity(it)
                    }
                    dao.insertAllProducts(updatedProducts)
                    productsFromDB = dao.getProducts().map { EntityMapper.toDomainDTO(it) }

                    ApiResult.Success(productsFromDB)
                }

            }
            else -> {
                ApiResult.Error(result.toString())
            }
        }
    }

    override suspend fun getProductById(guid: String): ProductInListDomainDTO {
        val product = dao.getProductByGuid(guid)
        return product.let { EntityMapper.toDomainDTO(it) }
    }

    override suspend fun toggleFavorite(guid: String, isFavorite: Boolean) {
        dao.updateFavoriteStatus(guid, isFavorite)
    }
}

