package alex.android.lab.di.modules.presentationModules

import alex.android.lab.di.scopes.FeatureScope
import alex.android.lab.domain.UiStates.UIStatesMapper.UIStatesMapper
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.domain.interactors.ProductsInteractorImpl
import alex.android.lab.domain.interactors.cart.CartInteractor
import alex.android.lab.domain.interactors.cart.CartInteractorImpl
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.domain.repositories.cart.CartRepository
import dagger.Module
import dagger.Provides

@Module
class PdpModule {
    @Provides
    @FeatureScope
    fun provideProductsInteractor(
        productsRepository: ProductsRepository,
        uiStatesMapper: UIStatesMapper
    ): ProductsInteractor = ProductsInteractorImpl(productsRepository, uiStatesMapper)

    @Provides
    @FeatureScope
    fun provideCartInteractor(cartRepository: CartRepository): CartInteractor = CartInteractorImpl(cartRepository)
}