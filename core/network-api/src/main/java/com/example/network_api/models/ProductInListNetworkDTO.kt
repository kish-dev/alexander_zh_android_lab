package com.example.network_api.models

data class ProductInListNetworkDto (
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val viewCount: Int,
    val inCartCount: Int
)