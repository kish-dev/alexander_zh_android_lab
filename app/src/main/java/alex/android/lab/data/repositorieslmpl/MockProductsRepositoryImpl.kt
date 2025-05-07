package alex.android.lab.data.repositorieslmpl

import alex.android.lab.data.DataSource.LocalDataSource.Dao
import alex.android.lab.data.DataSource.LocalDataSource.mappers.EntityMapper
import alex.android.lab.data.DataSource.RemoteDataSource.RemoteDataSource
import alex.android.lab.data.mappers.ProductListMapper
import alex.android.lab.domain.UiStates.ApiResult
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.presentation.viewObject.ProductInListVO
import org.koin.core.component.KoinComponent


class MockProductsRepositoryImpl (
    private val remoteDataSource: RemoteDataSource,
    private val dao: Dao
): ProductsRepository, KoinComponent {

    override suspend fun getProductsDB(): ApiResult<List<ProductInListVO>> {
        return ApiResult.Success(dao.getProducts().map { EntityMapper.toVO(it) })
    }

    override suspend fun incrementViewCount(guid: String) {
        dao.incrementViewCount(guid)
    }

    override suspend fun checkProductsChanges(
        productsFromApi: List<ProductInListVO>,
        productsFromDb: List<ProductInListVO>
    ): List<ProductInListVO> {

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

    override suspend fun getProducts(): ApiResult<List<ProductInListVO>> {

        return when (val result = remoteDataSource.getProducts()) {
            is ApiResult.Success -> {

                val productsFromApi = result.data.map { ProductListMapper.toVO(it) }
                var productsFromDB = dao.getProducts().map { EntityMapper.toVO(it) }

                if (productsFromDB.isEmpty()) {

                    val productsEntity = productsFromApi.map { EntityMapper.toDbEntity(it) }
                    dao.insertAllProducts(productsEntity)

                    productsFromDB = dao.getProducts().map { EntityMapper.toVO(it) }
                    ApiResult.Success(productsFromDB)

                } else {

                    val updatedProducts = checkProductsChanges(productsFromApi, productsFromDB).map {
                        EntityMapper.toDbEntity(it)
                    }
                    dao.insertAllProducts(updatedProducts)
                    productsFromDB = dao.getProducts().map { EntityMapper.toVO(it) }

                    ApiResult.Success(productsFromDB)
                }

            }
            else -> {
                ApiResult.Error(result.toString())
            }
        }
    }

    override suspend fun getProductById(guid: String): ProductInListVO {
        val product = dao.getProductByGuid(guid)
        return product.let { EntityMapper.toVO(it) }
    }

    override suspend fun toggleFavorite(guid: String, isFavorite: Boolean) {
        dao.updateFavoriteStatus(guid, isFavorite)
    }
}

