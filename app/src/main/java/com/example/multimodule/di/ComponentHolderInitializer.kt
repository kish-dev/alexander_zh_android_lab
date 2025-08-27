package com.example.multimodule.di

import com.example.navigation_impl.di.NavigationImplComponentHolder
import com.example.navigation_impl.di.NavigationImplDependencies
import com.example.productslistscreen_impl.di.ProductsScreenComponentHolder
import com.example.productslistscreen_impl.di.ProductsScreenDependencies
import javax.inject.Inject
import javax.inject.Provider

internal class ComponentHolderInitializer @Inject constructor(
    private val navigationDependencies: Provider<NavigationImplDependencies>,
    private val productsScreenDependencies: Provider<ProductsScreenDependencies>,
//    private val feature2Dependencies: Provider<Feature2Dependencies>,
//    private val feature3Dependencies: Provider<Feature3Dependencies>,
) {

    fun init() {
        NavigationImplComponentHolder.init(navigationDependencies)
        ProductsScreenComponentHolder.init(productsScreenDependencies)
//        Feature2ComponentHolder.init(feature2Dependencies)
//        Feature3ComponentHolder.init(feature3Dependencies)
    }
}