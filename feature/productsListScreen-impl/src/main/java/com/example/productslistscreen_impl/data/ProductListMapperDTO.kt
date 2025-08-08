package com.example.productScreen_impl.data

import com.example.database_api.models.ProductInListDbDTO
import com.example.database_api.models.ProductInListEntity
import com.example.network_api.models.ProductInListNetworkDto
import com.example.productslistscreen_impl.domain.models.ProductInListDomainDTO
import javax.inject.Inject

class ProductListMapperDTO @Inject constructor(){

    fun DomainDtoToDB(products: ProductInListDomainDTO) : ProductInListDbDTO {
        return ProductInListDbDTO(
            guid = products.guid,
            image = products.image,
            name = products.name,
            price = products.price,
            rating = products.rating,
            isFavorite = products.isFavorite,
            isInCart = products.isInCart,
            viewCount = products.viewCount,
            inCartCount = products.inCartCount
        )
    }
    fun DbToDomainDTO(products: ProductInListEntity) : ProductInListDomainDTO {
        return ProductInListDomainDTO(
            guid = products.guid,
            image = products.image,
            name = products.name,
            price = products.price,
            rating = products.rating,
            isFavorite = products.isFavorite,
            isInCart = products.isInCart,
            viewCount = products.viewCount,
            inCartCount = products.inCartCount
        )
    }
    fun NetworkToDomainDTO(products: ProductInListNetworkDto) : ProductInListDomainDTO {
        return ProductInListDomainDTO(
            guid = products.guid,
            image = products.image,
            name = products.name,
            price = products.price,
            rating = products.rating,
            isFavorite = products.isFavorite,
            isInCart = products.isInCart,
            viewCount = products.viewCount,
            inCartCount = products.inCartCount
        )
    }
}