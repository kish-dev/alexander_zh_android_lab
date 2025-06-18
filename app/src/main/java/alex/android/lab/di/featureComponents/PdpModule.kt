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
import alex.android.lab.presentation.view.PdpFragment
import alex.android.lab.presentation.viewModel.PdpViewModel
import dagger.Component
import dagger.Module
import dagger.Provides

@FeatureScope
@Component(
    dependencies = [AppComponent::class],
    modules = [PdpModule::class]
)
interface PdpComponent {
    fun inject(fragment: PdpFragment)

    fun getPdpViewModel(): PdpViewModel
}

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

    @Provides
    @FeatureScope
    fun providePdpViewModel(
        productsInteractor: ProductsInteractor,
        cartInteractor: CartInteractor,
        productListMapper: ProductListMapper
    ): PdpViewModel = PdpViewModel(productsInteractor, cartInteractor, productListMapper)
}