package com.example.productslistscreen_impl.di

import com.example.di.BaseComponentHolder

object ProductsScreenComponentHolder : BaseComponentHolder<
        ProductsListScreenApi,
        ProductsScreenDependencies,
        >() {

    override fun build(dependencies: ProductsScreenDependencies):
            ProductsListScreenApi = DaggerProductsScreenComponent.factory().create(
        dependencies,
        dependencies.networkProvider,
        dependencies.databaseProvider,
            )
}