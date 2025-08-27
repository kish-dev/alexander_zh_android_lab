package com.example.navigation_impl.di

import androidx.navigation.NavController
import com.example.navigation_api.NavigationApi
import com.example.navigation_impl.NavigationActivityProvider
import com.example.navigation_impl.featureNavigation.ProductsFragmentNavigation
import com.example.productslistscreen_impl.navigation.ProductsScreenDirections
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [NavigationImplModule.Binder::class],
)
internal class NavigationImplModule {

    @Provides
    fun provideNavController(
        activityProvider: NavigationActivityProvider,
    ): NavController = activityProvider.get()
        ?.getNavigationFragment()
        ?.navController
        ?: error("Do not make navigation calls while activity is not available")

    @Module
    interface Binder {

        @Binds
        fun bindProductsScreenNavigationApi(api: ProductsFragmentNavigation): NavigationApi<ProductsScreenDirections>

//        @Binds
//        fun bindFeature2NavigationApi(api: Feature2NavigationImpl): NavigationApi<Feature2Directions>
//
//        @Binds
//        fun bindFeature3NavigationApi(api: Feature3NavigationImpl): NavigationApi<Feature3Directions>
    }
}