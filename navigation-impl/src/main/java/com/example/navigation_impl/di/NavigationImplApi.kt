package com.example.navigation_impl.di

import com.example.di.BaseApi
import com.example.navigation_api.NavigationApi
import com.example.productslistscreen_impl.navigation.ProductsScreenDirections

interface NavigationImplApi : BaseApi {

    val productsScreenNavigationApi: NavigationApi<ProductsScreenDirections>

//    val feature2NavigationApi: NavigationApi<Feature2Directions>
//
//    val feature3NavigationApi: NavigationApi<Feature3Directions>
}