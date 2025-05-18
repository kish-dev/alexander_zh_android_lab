package alex.android.lab.data.DataSource.LocalDataSource.mappers

import alex.android.lab.data.DataSource.LocalDataSource.ProductInListEntity
import alex.android.lab.domain.dto.ProductInListDomainDTO
import alex.android.lab.presentation.viewObject.ProductInListVO

object EntityMapper {
    fun toDbEntity(product: ProductInListDomainDTO): ProductInListEntity {
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

    fun toDomainDTO(product: ProductInListEntity): ProductInListDomainDTO {
        return ProductInListDomainDTO(
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