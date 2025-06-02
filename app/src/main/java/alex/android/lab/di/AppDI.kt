package alex.android.lab.di

import alex.android.lab.presentation.mappers.ProductListMapper
import alex.android.lab.presentation.viewModel.CartViewModel
import alex.android.lab.presentation.viewModel.PdpViewModel
import alex.android.lab.presentation.viewModel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel <PdpViewModel> {
        PdpViewModel(productsInteractor = get(), cartInteractor = get(), productListMapper = get())
    }
    viewModel <ProductsViewModel> {
        ProductsViewModel(productsInteractor = get(), cartInteractor = get(), productListMapper = get())
    }
    viewModel <CartViewModel> {
        CartViewModel(productsInteractor = get(), cartInteractor = get(), productListMapper = get())
    }
    single <ProductListMapper> {
        ProductListMapper()
    }
}
