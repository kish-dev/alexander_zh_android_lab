package com.example.navigation_impl.featureNavigation

import androidx.navigation.NavController
import com.example.navigation_api.NavigationApi
import com.example.productslistscreen_impl.navigation.ProductsScreenDirections
import com.example.productslistscreen_impl.presentation.ProductsFragmentDirections
import javax.inject.Inject
import javax.inject.Provider

internal class ProductsFragmentNavigation @Inject constructor(
    private val navController: Provider<NavController>,
): NavigationApi<ProductsScreenDirections> {

    override fun navigate(direction: ProductsScreenDirections) {
        when (direction) {
            is ProductsScreenDirections.ToPdpScreen -> {
                navController.get().navigate(
                    ProductsFragmentDirections.actionProductsFragmentToPDPFragment(
                        direction.id
                    )
                )
            }
            ProductsScreenDirections.ToCartScreen -> {
                navController.get().navigate(
                    ProductsFragmentDirections.actionProductsFragmentToCartFragment()
                )
            }
        }
    }


}