package com.example.database_api.models

data class ProductInListDbDTO (
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