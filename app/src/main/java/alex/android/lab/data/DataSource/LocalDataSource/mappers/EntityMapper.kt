package alex.android.lab.data.DataSource.LocalDataSource.mappers

import alex.android.lab.data.DataSource.LocalDataSource.ProductInListEntity
import alex.android.lab.presentation.viewObject.ProductInListVO

object EntityMapper {
    fun toDbEntity(product: ProductInListVO): ProductInListEntity {
        return ProductInListEntity(
            guid = product.guid,
            image = product.image,
            name = product.name,
            price = product.price,
            rating = product.rating,
            isFavorite = product.isFavorite,
            isInCart = product.isInCart,
            viewCount = product.viewCount
        )
    }

    fun toVO(product: ProductInListEntity): ProductInListVO {
        return ProductInListVO(
            guid = product.guid,
            image = product.image,
            name = product.name,
            price = product.price,
            rating = product.rating,
            isFavorite = product.isFavorite,
            isInCart = product.isInCart,
            viewCount = product.viewCount
        )
    }
}