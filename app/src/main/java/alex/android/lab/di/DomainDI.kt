package alex.android.lab.di

import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.domain.interactors.ProductsInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    factory<ProductsInteractor> {
        ProductsInteractorImpl(productsRepository = get(), uiStatesMapper = get())
    }
}