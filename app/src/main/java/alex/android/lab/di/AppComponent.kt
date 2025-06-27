package alex.android.lab.di

import alex.android.lab.di.modules.dataSourceModules.DatabaseModule
import alex.android.lab.di.modules.dataSourceModules.NetworkModule
import alex.android.lab.di.modules.dataSourceModules.RepositoryModule
import alex.android.lab.di.scopes.AppScope
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.domain.repositories.cart.CartRepository
import android.app.Application
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun productsRepository(): ProductsRepository
    fun cartRepository(): CartRepository
}