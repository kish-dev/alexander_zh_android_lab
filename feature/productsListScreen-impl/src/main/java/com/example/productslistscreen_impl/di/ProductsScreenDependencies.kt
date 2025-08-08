package com.example.productslistscreen_impl.di

import androidx.lifecycle.ViewModelProvider
import com.example.database_api.DatabaseApi
import com.example.database_api.DatabaseProvider
import com.example.di.BaseDependencies
import com.example.navigation_api.NavigationApi
import com.example.network_api.NetworkProvider
import com.example.network_api.remote.RemoteDataSourceApi
import com.example.productScreen_impl.data.ProductListMapperDTO
import com.example.productScreen_impl.data.UIStatesMapper
import com.example.productslistscreen_impl.domain.interactor.CartInteractor
import com.example.productslistscreen_impl.domain.interactor.ProductsInteractor
import com.example.productslistscreen_impl.domain.repository.CartRepository
import com.example.productslistscreen_impl.domain.repository.ProductsRepository
import com.example.productslistscreen_impl.navigation.ProductsScreenDirections
import com.example.productslistscreen_impl.presentation.mappers.ProductListMapper

interface ProductsScreenDependencies : BaseDependencies {
    val navigationApi: NavigationApi<ProductsScreenDirections>

    val networkProvider: NetworkProvider
    val databaseProvider: DatabaseProvider
    // Репозитории
}