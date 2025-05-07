package alex.android.lab.di

import alex.android.lab.presentation.viewModel.ErrorPageViewModel
import alex.android.lab.presentation.viewModel.LoadingViewModel
import alex.android.lab.presentation.viewModel.PdpViewModel
import alex.android.lab.presentation.viewModel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel <LoadingViewModel> {
        LoadingViewModel(interactor = get())
    }
    viewModel <PdpViewModel> {
        PdpViewModel(interactor = get())
    }
    viewModel <ProductsViewModel> {
        ProductsViewModel(interactor = get())
    }
    viewModel <ErrorPageViewModel> {
        ErrorPageViewModel()
    }
}