package com.example.productslistscreen_impl.di

import com.example.productslistscreen_impl.presentation.ProductsFragment
import com.example.network_api.NetworkProvider
import com.example.database_api.DatabaseProvider
import dagger.Component

@Component(
    dependencies = [
        ProductsScreenDependencies::class,
        NetworkProvider::class,
        DatabaseProvider::class
    ],
    modules = [ProductsScreenModule::class]
)
interface ProductsScreenComponent : ProductsListScreenApi {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: ProductsScreenDependencies,
            provider: NetworkProvider,
            databaseProvider: DatabaseProvider
        ): ProductsScreenComponent
    }
}