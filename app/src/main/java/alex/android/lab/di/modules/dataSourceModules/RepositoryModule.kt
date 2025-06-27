package alex.android.lab.di.modules.dataSourceModules

import alex.android.lab.data.repositorieslmpl.CartRepositoryImpl
import alex.android.lab.data.repositorieslmpl.ProductsRepositoryImpl
import alex.android.lab.di.scopes.AppScope
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.domain.repositories.cart.CartRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    @AppScope
    abstract fun bindProductsRepository(impl: ProductsRepositoryImpl): ProductsRepository

    @Binds
    @AppScope
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository
}
