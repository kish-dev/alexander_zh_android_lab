package alex.android.lab.domain.dto

data class ProductInListDomainDTO (
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val viewCount: Int
)