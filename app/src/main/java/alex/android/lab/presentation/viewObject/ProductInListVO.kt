package alex.android.lab.presentation.viewObject

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductInListVO(
    var guid: String,
    var image: String,
    var name: String,
    var price: String,
    var rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val viewCount: Int,
    val inCartCount: Int
): Parcelable
