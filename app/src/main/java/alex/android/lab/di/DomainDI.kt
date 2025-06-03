package alex.android.lab.di

import alex.android.lab.domain.UiStates.UIStatesMapper.UIStatesMapper
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.domain.interactors.ProductsInteractorImpl
import alex.android.lab.domain.interactors.cart.CartInteractor
import alex.android.lab.domain.interactors.cart.CartInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    factory<ProductsInteractor> {
        ProductsInteractorImpl(productsRepository = get(), uiStatesMapper = get())
    }
    factory<CartInteractor> {
        CartInteractorImpl(cartRepository = get())
    }
    single <UIStatesMapper> {
        UIStatesMapper()
    }
}