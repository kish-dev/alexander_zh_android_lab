package alex.android.lab.presentation.mappers

import alex.android.lab.data.dto.ProductInListDTO
import alex.android.lab.domain.dto.ProductInListDomainDTO
import alex.android.lab.presentation.UiStates.UIStates
import alex.android.lab.presentation.viewObject.ProductInListVO

object ProductListMapper {
    fun toVO(products: ProductInListDomainDTO) : ProductInListVO {
        return ProductInListVO(
            guid = products.guid,
            image = products.image,
            name = products.name,
            price = products.price,
            rating = products.rating,
            isFavorite = products.isFavorite,
            isInCart = products.isInCart,
            viewCount = products.viewCount

        )
    }
    fun mapUiStateDTOtoVO( products: UIStates<List<ProductInListDomainDTO>>): UIStates<List<ProductInListVO>> {
        when (products) {
            is UIStates.Loading -> {
                return UIStates.Loading()
            }
            is UIStates.Success -> {
                return UIStates.Success(
                    products.data.map { ProductListMapper.toVO(it) })
            }
            is UIStates.Error -> {
                return UIStates.Error(products.error)
            }
        }
        return UIStates.Error("Unknown error")
    }
}