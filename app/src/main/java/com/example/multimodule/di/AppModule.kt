package com.example.multimodule.di

import androidx.lifecycle.ViewModelProvider
import com.example.database_api.DatabaseApi
import com.example.database_api.DatabaseProvider
import com.example.database_impl.di.DatabaseComponent
import com.example.navigation_api.NavigationApi
import com.example.navigation_impl.NavigationActivityProvider
import com.example.navigation_impl.di.NavigationImplComponentHolder
import com.example.navigation_impl.di.NavigationImplDependencies
import com.example.network_api.NetworkProvider
import com.example.network_api.remote.RemoteDataSourceApi
import com.example.network_impl.di.NetworkComponent
import com.example.productScreen_impl.data.ProductListMapperDTO
import com.example.productScreen_impl.data.UIStatesMapper
import com.example.productslistscreen_impl.di.ProductsScreenComponentHolder
import com.example.productslistscreen_impl.di.ProductsScreenDependencies
import com.example.productslistscreen_impl.domain.interactor.CartInteractor
import com.example.productslistscreen_impl.domain.interactor.ProductsInteractor
import com.example.productslistscreen_impl.domain.repository.CartRepository
import com.example.productslistscreen_impl.domain.repository.ProductsRepository
import com.example.productslistscreen_impl.navigation.ProductsScreenDirections
import com.example.productslistscreen_impl.presentation.mappers.ProductListMapper
import dagger.Module
import dagger.Provides


@Module
internal class AppModule {

    @Provides
    fun provideNavigationDependencies(
        activityProvider: NavigationActivityProvider,
    ): NavigationImplDependencies = object : NavigationImplDependencies {
        override val activityProvider: NavigationActivityProvider = activityProvider
    }

    @Provides
    fun provideProductsScreenDependencies(): ProductsScreenDependencies=
        object : ProductsScreenDependencies {
            override val navigationApi: NavigationApi<ProductsScreenDirections> =
                NavigationImplComponentHolder.get().productsScreenNavigationApi
            override val networkProvider: NetworkProvider = NetworkComponent.get()
            override val databaseProvider: DatabaseProvider = DatabaseComponent.get()
        }

}