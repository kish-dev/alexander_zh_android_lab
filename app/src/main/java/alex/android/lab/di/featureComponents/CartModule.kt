package alex.android.lab.di.featureComponents

import alex.android.lab.di.AppComponent
import alex.android.lab.di.FeatureScope
import alex.android.lab.domain.UiStates.UIStatesMapper.UIStatesMapper
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.domain.interactors.ProductsInteractorImpl
import alex.android.lab.domain.interactors.cart.CartInteractor
import alex.android.lab.domain.interactors.cart.CartInteractorImpl
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.domain.repositories.cart.CartRepository
import alex.android.lab.presentation.mappers.ProductListMapper
import alex.android.lab.presentation.view.CartFragment
import alex.android.lab.presentation.viewModel.CartViewModel
import dagger.Component
import dagger.Module
import dagger.Provides

@FeatureScope
@Component(
    dependencies = [AppComponent::class],
    modules = [CartModule::class]
)
interface CartComponent {
    fun inject(fragment: CartFragment)

    fun getCartViewModel(): CartViewModel
}

@Module
class CartModule {
    @Provides
    @FeatureScope
    fun provideProductsInteractor(
        productsRepository: ProductsRepository,
        uiStatesMapper: UIStatesMapper
    ): ProductsInteractor = ProductsInteractorImpl(productsRepository, uiStatesMapper)

    @Provides
    @FeatureScope
    fun provideCartInteractor(cartRepository: CartRepository): CartInteractor = CartInteractorImpl(cartRepository)

    @Provides
    @FeatureScope
    fun provideCartViewModel(
        productsInteractor: ProductsInteractor,
        cartInteractor: CartInteractor,
        productListMapper: ProductListMapper
    ): CartViewModel = CartViewModel(productsInteractor, cartInteractor, productListMapper)
}