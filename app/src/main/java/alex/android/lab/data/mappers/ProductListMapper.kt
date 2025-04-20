package alex.android.lab.data.mappers

import alex.android.lab.data.dto.ProductInListDTO
import alex.android.lab.presentation.viewObject.ProductInListVO

object ProductListMapper {
    fun toVO(products: ProductInListDTO) : ProductInListVO {
        return ProductInListVO(
            guid = products.guid,
            image = products.image,
            name = products.name,
            price = products.price,
            rating = products.rating,
            isFavorite = products.isFavorite,
            isInCart = products.isInCart)
    }
}