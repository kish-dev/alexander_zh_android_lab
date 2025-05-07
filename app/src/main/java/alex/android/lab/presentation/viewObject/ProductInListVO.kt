package alex.android.lab.presentation.viewObject

import android.os.Parcel
import android.os.Parcelable

data class ProductInListVO(
    var guid: String,
    var image: String,
    var name: String,
    var price: String,
    var rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val viewCount: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(guid)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeDouble(rating)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeByte(if (isInCart) 1 else 0)
        parcel.writeInt(viewCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductInListVO> {
        override fun createFromParcel(parcel: Parcel): ProductInListVO {
            return ProductInListVO(parcel)
        }

        override fun newArray(size: Int): Array<ProductInListVO?> {
            return arrayOfNulls(size)
        }
    }
}
