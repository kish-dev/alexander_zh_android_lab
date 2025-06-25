package alex.android.lab.di.modules.dataSourceModules

import alex.android.lab.data.DataSource.LocalDataSource.Dao
import alex.android.lab.data.DataSource.LocalDataSource.mappers.EntityMapper
import alex.android.lab.data.DataSource.RemoteDataSource.RemoteDataSource
import alex.android.lab.data.mappers.ProductListMapperDTO
import alex.android.lab.data.repositorieslmpl.CartRepositoryImpl
import alex.android.lab.data.repositorieslmpl.ProductsRepositoryImpl
import alex.android.lab.di.scopes.AppScope
import alex.android.lab.di.scopes.FeatureScope
import alex.android.lab.domain.repositories.ProductsRepository
import alex.android.lab.domain.repositories.cart.CartRepository
import alex.android.lab.presentation.mappers.ProductListMapper
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class RepositoryModule {
    @Binds
    @AppScope
    abstract fun bindProductsRepository(impl: ProductsRepositoryImpl): ProductsRepository

    @Binds
    @AppScope
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository
}
