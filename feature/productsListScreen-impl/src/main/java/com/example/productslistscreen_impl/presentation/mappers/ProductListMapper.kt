package com.example.productslistscreen_impl.presentation.mappers

import com.example.productslistscreen_impl.domain.models.ProductInListDomainDTO
import com.example.productslistscreen_impl.domain.models.UIStates
import com.example.productslistscreen_impl.presentation.models.ProductInListVo
import javax.inject.Inject

class ProductListMapper @Inject constructor(){
    fun toVO(products: ProductInListDomainDTO) : ProductInListVo {
        return ProductInListVo(
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
    fun mapUiStateDTOtoVO( products: UIStates<List<ProductInListDomainDTO>>): UIStates<List<ProductInListVo>> {
        when (products) {
            is UIStates.Loading -> {
                return UIStates.Loading()
            }
            is UIStates.Success -> {
                return UIStates.Success(
                    products.data.map { toVO(it) })
            }
            is UIStates.Error -> {
                return UIStates.Error(products.error)
            }
        }
        return UIStates.Error("Unknown error")
    }
}