package com.example.database_impl

import com.example.database_api.mappers.Mapper
import com.example.database_api.models.ProductInListDbDTO
import com.example.database_api.models.ProductInListEntity

class MapperImpl(): Mapper {

    override fun toEntity(dto: ProductInListDbDTO): ProductInListEntity {
        return ProductInListEntity(
            guid = dto.guid,
            image = dto.image,
            name = dto.name,
            price = dto.price,
            rating = dto.rating,
            isFavorite = dto.isFavorite,
            isInCart = dto.isInCart,
            viewCount = dto.viewCount,
            inCartCount = dto.inCartCount
        )
    }
}
