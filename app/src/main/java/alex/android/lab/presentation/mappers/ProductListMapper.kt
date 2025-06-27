package alex.android.lab.presentation.mappers

import alex.android.lab.domain.UiStates.UIStates
import alex.android.lab.domain.dto.ProductInListDomainDTO
import alex.android.lab.presentation.viewObject.ProductInListVO
import javax.inject.Inject

class ProductListMapper @Inject constructor(){
    fun toVO(products: ProductInListDomainDTO) : ProductInListVO {
        return ProductInListVO(
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
    fun mapUiStateDTOtoVO( products: UIStates<List<ProductInListDomainDTO>>): UIStates<List<ProductInListVO>> {
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