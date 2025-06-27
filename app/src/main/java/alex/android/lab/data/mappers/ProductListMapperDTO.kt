package alex.android.lab.data.mappers

import alex.android.lab.data.dto.ProductInListDTO
import alex.android.lab.domain.dto.ProductInListDomainDTO
import javax.inject.Inject

class ProductListMapperDTO @Inject constructor(){
    fun toDomainDTO(products: ProductInListDTO) : ProductInListDomainDTO {
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