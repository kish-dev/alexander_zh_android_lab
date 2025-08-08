package com.example.productslistscreen_impl.navigation

sealed interface ProductsScreenDirections {
    object ToCartScreen : ProductsScreenDirections
    data class ToPdpScreen(val id: String) : ProductsScreenDirections
}