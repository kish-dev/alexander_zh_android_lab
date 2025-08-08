package com.example.productslistscreen_impl.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.database_api.DatabaseApi
import com.example.network_api.remote.RemoteDataSourceApi
import com.example.productScreen_impl.data.CartRepositoryImpl
import com.example.productScreen_impl.data.ProductListMapperDTO
import com.example.productScreen_impl.data.ProductsRepositoryImpl
import com.example.productScreen_impl.data.UIStatesMapper
import com.example.productScreen_impl.domain.CartInteractorImpl
import com.example.productScreen_impl.domain.ProductsInteractorImpl
import com.example.productslistscreen_impl.domain.interactor.CartInteractor
import com.example.productslistscreen_impl.domain.interactor.ProductsInteractor
import com.example.productslistscreen_impl.domain.repository.CartRepository
import com.example.productslistscreen_impl.domain.repository.ProductsRepository
import com.example.productslistscreen_impl.presentation.mappers.ProductListMapper
import com.example.productslistscreen_impl.presentation.viewModel.ProductsViewModel
import dagger.Module
import dagger.Provides

@Module
class ProductsScreenModule {

    @Provides
    fun provideProductsRepository(
        remoteDataSource: RemoteDataSourceApi,
        productListMapper: ProductListMapperDTO,
        dao: DatabaseApi
    ): ProductsRepository = ProductsRepositoryImpl(remoteDataSource, productListMapper, dao)

    @Provides
    fun provideCartRepository(dao: DatabaseApi): CartRepository = CartRepositoryImpl(dao)

    @Provides
    fun provideProductsInteractor(
        repository: ProductsRepository,
        mapper: UIStatesMapper
    ): ProductsInteractor = ProductsInteractorImpl(repository, mapper)

    @Provides
    fun provideCartInteractor(repository: CartRepository): CartInteractor =
        CartInteractorImpl(repository)

    @Provides
    fun provideProductListMapper(): ProductListMapper = ProductListMapper()

    @Provides
    fun provideProductListMapperDTO(): ProductListMapperDTO = ProductListMapperDTO()

    @Provides
    fun provideUIStatesMapper(): UIStatesMapper = UIStatesMapper()

    @Provides
    fun provideViewModelFactory(
        productsInteractor: ProductsInteractor,
        cartInteractor: CartInteractor,
        productListMapper: ProductListMapper
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductsViewModel(
                    productsInteractor,
                    cartInteractor,
                    productListMapper
                ) as T
            }
        }
    }
}