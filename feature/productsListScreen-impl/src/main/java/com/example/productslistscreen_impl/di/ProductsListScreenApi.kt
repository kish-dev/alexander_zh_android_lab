package com.example.productslistscreen_impl.di

import com.example.di.BaseApi
import com.example.productslistscreen_impl.presentation.ProductsFragment

interface ProductsListScreenApi : BaseApi {

    fun inject(fragment: ProductsFragment)
}