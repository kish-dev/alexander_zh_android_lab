package com.example.productslistscreen_impl.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductInListVo(
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

