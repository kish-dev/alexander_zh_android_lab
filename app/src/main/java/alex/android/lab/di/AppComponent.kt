package alex.android.lab.di

import alex.android.lab.di.featureComponents.PdpComponent
import alex.android.lab.di.modules.AppModule
import alex.android.lab.di.modules.MapperModule
import alex.android.lab.di.modules.dataSourceModules.DatabaseModule
import alex.android.lab.di.modules.dataSourceModules.NetworkModule
import alex.android.lab.di.modules.dataSourceModules.RepositoryModule
import alex.android.lab.di.scopes.AppScope
import alex.android.lab.di.viewModelModules.ViewModelFactoryModule
import alex.android.lab.di.viewModelModules.ViewModelModule
import alex.android.lab.domain.UiStates.UIStatesMapper.UIStatesMapper
import alex.android.lab.domain.interactors.ProductsInteractor
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.domain.repositories.cart.CartRepository
import alex.android.lab.presentation.mappers.ProductListMapper
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Component

@AppScope
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        MapperModule::class
    ]
)
interface AppComponent {
    fun productsRepository(): ProductsRepository
    fun cartRepository(): CartRepository
    fun productListMapper(): ProductListMapper
    fun uiStatesMapper(): UIStatesMapper
    fun context(): Context
}