package alex.android.lab.di

import alex.android.lab.domain.UiStates.UIStatesMapper.UIStatesMapper
import alex.android.lab.presentation.mappers.ProductListMapper
import alex.android.lab.presentation.viewModel.PdpViewModel
import alex.android.lab.presentation.viewModel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel <PdpViewModel> {
        PdpViewModel(productsInteractor = get(), productListMapper = get())
    }
    viewModel <ProductsViewModel> {
        ProductsViewModel(productsInteractor = get(), productListMapper = get())
    }
    single <UIStatesMapper> {
        UIStatesMapper()
    }
    single <ProductListMapper> {
        ProductListMapper()
    }
}
